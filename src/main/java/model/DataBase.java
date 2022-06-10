package model;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    public static void loadDatabase() {
        //If the database doesnt exists we create it
        String url = "jdbc:sqlite:./dictionary.db";

        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {

                DatabaseMetaData meta = connection.getMetaData();
                //System.out.println("The driver name is " + meta.getDriverName());
                //System.out.println("A new database has been created.");

                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                //statement.executeUpdate("create table catalog (id INTEGER PRIMARY KEY AUTOINCREMENT, title string, extract string, source integer)");
                statement.executeUpdate("create table catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
                //If the DB was created before, a SQL error is reported but it is not harmfull...
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> getTitles()
    {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from catalog");
            while(rs.next()) titles.add(rs.getString("title"));
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
            return titles;
        }
    }

    public static void saveInfo(String title, String extract) throws SQLException {
        String formattedTitle = title.replace("'", "`");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("replace into catalog values(null, '"+ formattedTitle + "', '"+ extract + "', 1)");

        }
        catch(SQLException e)
        {
            System.err.println("Error saving " + e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println( e);
            }
        }
    }

    public static String getExtract(String title)
    {

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
            rs.next();
            return rs.getString("extract");
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println("Get title error " + e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }

    public static void deleteEntry(String title)
    {

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println("Get title error " + e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }

}
