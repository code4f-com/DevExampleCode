/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.call.function.mysql;

import com.tuanpla.utils.DBPool;
import java.sql.*;

/**
 *
 * @author TUANPLA
 */
public class DevFunction {

    public static void main(String[] args) {
        try {
            Connection conn = DBPool.getConnection();
            CallableStatement castm = conn.prepareCall("{ ? = call NEXTVAL(?)}");
            int count = 0;
            castm.setString(2, "VTE");
            castm.registerOutParameter(1, Types.INTEGER);
            castm.execute();
            count = castm.getInt(1);
            System.out.println("Sequence:" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
