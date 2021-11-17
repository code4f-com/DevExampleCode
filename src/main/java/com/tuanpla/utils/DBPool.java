package com.tuanpla.utils;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class DBPool {

    //=============================Connection Pool------------------------------------------
    private static final HashMap<Connection, TimeWrapper> manageTimeOut = new HashMap<>();
    static LinkedList<Connection> pool = new LinkedList();
    public static int MAX_CONNECTIONS = 5;
    public static int INI_CONNECTIONS = 3;
    private static int IDLE_TIME_OUT = 180;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static String user = "root";
    private static String pass = "";
    private static String url = "jdbc:mysql://localhost:3306/db_xuly_dulieu?useUnicode=true&characterEncoding=UTF-8";

    static {
        try {

            // Convert second to militime
            IDLE_TIME_OUT = DBPool.IDLE_TIME_OUT * 1000;
            Connection conn;
            synchronized (pool) {
                for (int i = 0; i < DBPool.INI_CONNECTIONS; i++) {
                    conn = makeDBConnection();
                    if (conn != null) {
                        manageTimeOut.put(conn, new TimeWrapper(DBPool.IDLE_TIME_OUT));
                        pool.addLast(conn);
                    }
                }
                Tool.Debug("DBool init:" + DBPool.INI_CONNECTIONS + " connection");
                pool.notifyAll();
            }
            new DestroyObject().start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static synchronized Connection getConnection() {
        Connection conn;
        synchronized (pool) {
            if (pool.isEmpty()) {
                conn = makeDBConnection();
            } else {
                conn = pool.removeFirst();
            }
        }
        return conn;
    }

    private static synchronized void putConnection(Connection conn) {

        try { // Ignore closed connection
            if (conn == null || conn.isClosed()) {
                Tool.Debug("[DBPool].putConnection: conn is null or closed: ");
            } else {
                // Chua synchronized nen dung DBPool.size()
                if (DBPool.size() >= MAX_CONNECTIONS) {
                    // Remove khi dong Connection
                    manageTimeOut.remove(conn);
                    conn.close();
                } else {
                    synchronized (pool) {
//                        System.out.println("putConnection:" + conn.toString() + "| Size =" + pool.size() + "|" + DateProc.createTimestamp());
                        // Cap nhat thoi gian truy cap Connection khi put vao pool
                        TimeWrapper timeWap = manageTimeOut.remove(conn);
                        if (timeWap != null) {
                            timeWap.setLiveTime(IDLE_TIME_OUT);
                            manageTimeOut.put(conn, timeWap);
                        } else {
                            // Set Time truy cap 1 Connection Moi;
                            manageTimeOut.put(conn, new TimeWrapper(IDLE_TIME_OUT));
                        }
                        pool.addLast(conn);
                        pool.notifyAll();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void releadRsPstm(ResultSet rs, PreparedStatement pstm) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized int size() {
        synchronized (pool) {
            return pool.size();
        }
    }

    public static synchronized boolean isEmpty() {
        synchronized (pool) {
            return pool.isEmpty();
        }
    }

    public static void release() {
        synchronized (pool) {
            for (Connection conn : pool) {
                try {
                    if (conn != null) {
                        // Remove timeWapper khi dong Connection
                        manageTimeOut.remove(conn);
                        conn.close();
                    }
                } catch (SQLException e) {
                    Tool.Debug("[DBPool].release: Cannot close connection! (maybe closed?)" + e.getMessage());
                }
            }
            manageTimeOut.clear();
            pool.clear();
        }
    }

    public static void freeConn(ResultSet rs, PreparedStatement pstm, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                putConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    private static synchronized Connection makeDBConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            Tool.Debug("[DBPool].makeDBConnection: " + e.getMessage());
        }
        return conn;
    }

    private static class DestroyObject extends Thread {

        public DestroyObject() {
            this.setName("[wap.hoangton.vn] Monitor Conection:" + this.hashCode());
            this.setPriority(MIN_PRIORITY);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (pool) {
                        // Duyet qua tung conection
                        if (pool != null && pool.size() > 0) {
                            int k = 0;
                            while (k < pool.size() && pool.size() > 0) {
                                Connection conn = pool.get(k);
                                if (conn != null) {
                                    TimeWrapper timeWap = manageTimeOut.get(conn);
                                    if (timeWap != null) {
                                        if (timeWap.isExpired()) {
                                            Tool.Debug("DBPool- Close Connection IDLE time out!");
                                            manageTimeOut.remove(conn);
                                            pool.remove(conn);
                                            conn.close();
                                            if (pool.size() == INI_CONNECTIONS) {
                                                break;
                                            }
                                        } else {
                                            k++;
                                        }
                                    }
                                } else {
                                    Tool.Debug("co Connection bi null ??");
                                }
                            }
                            Tool.Debug("-----------" + DateProc.Timestamp2DDMMYYYYHH24MiSS(DateProc.createTimestamp()) + "----------------------------");
                            Tool.Debug(" - manageTimeOut Size = " + manageTimeOut.size());
                            Tool.Debug(" - Connection Size = " + pool.size());
                            Tool.Debug(" - Max Connection Size = " + MAX_CONNECTIONS);
                        }
                        pool.notifyAll();
                    }
                    sleep(3 * 60 * 1000);
                } catch (SQLException | InterruptedException e) {
                    Tool.Debug(e.getMessage());
                    try {
                        sleep(2 * 60 * 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
//            Tool.Debug(this.getName() + " Istop when Context distroy");
        }
    }
}
