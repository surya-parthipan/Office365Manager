package appLayer;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.String;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String tokenURL = "http://localhost:49806/api/OfficeLogin";
    private static final String usersURL = "http://localhost:49806/api/Users/";
    private static final String groupURL = "http://localhost:49806/api/Groups/";
    private static final String POST_URL = "http://localhost:49806/api/DemoLogin?username=surya&password=12345678";
    static String tenantAdd,appKey,clientID;

    public static void main(String[] args) {
        jsonify("189a915c-fe4f-4ffa-bde4-85b9628d07a0");
    }

    public static boolean grpcount(){
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url("https://graph.microsoft.com/v1.0/groups")
                    .get()
                    .addHeader("Authorization", sendGET(tokenURL).toString().replaceAll("\"",""))
                    .addHeader("api-Version", "1.6")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "73c077ec-7eb2-4b81-a02f-5a780e4c288d")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Response respon = client.newCall(request).execute();
            String result = respon.body().string();
//            System.out.println(result);
            JSONObject json = new JSONObject(result);
            int total_grp_count = (json.getJSONArray("value").toList().size());
            int dis_count=0,sec_count=0,office_count=0;
            for (int i = 0; i < total_grp_count; i++) {
                if(json.getJSONArray("value").getJSONObject(i).get("groupTypes").toString().length()>2){
                    office_count+=1;
                }
                if((json.getJSONArray("value").getJSONObject(i).get("mailEnabled").toString().equals("true"))&&(json.getJSONArray("value").getJSONObject(i).get("securityEnabled").toString().equals("false"))&&(json.getJSONArray("value").getJSONObject(i).get("groupTypes").toString().length()<=2)){
                    dis_count+=1;
                }
                if((json.getJSONArray("value").getJSONObject(i).get("mailEnabled").toString().equals("false"))&&(json.getJSONArray("value").getJSONObject(i).get("securityEnabled").toString().equals("true"))){
                    sec_count+=1;
                }
            }
            System.out.println(total_grp_count);
            System.out.println(office_count);
            System.out.println(dis_count);
            System.out.println(sec_count);

            try{
                java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123");
                Statement statement = conn.createStatement();
                String SQL = "update group_table set total_grp=?,sec_grp=?,dis_grp=?,off_grp=? where id=1 ";
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                {
                    pstmt.setInt(1,total_grp_count);
                    pstmt.setInt(2,sec_count);
                    pstmt.setInt(3,dis_count);
                    pstmt.setInt(4,office_count);
                }
                pstmt.executeUpdate();
                return true;
            }
            catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean usercount(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://graph.windows.net/myorganization/users")
                .get()
                .addHeader("Authorization", lictok().replaceAll("\"",""))
                .addHeader("api-Version", "1.6")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "73c077ec-7eb2-4b81-a02f-5a780e4c288d")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
//            System.out.println(res);
            JSONObject json = new JSONObject(res);
            int total_user_count = (json.getJSONArray("value").toList().size());
            int licensed_user_count=0,active_user_count=0;
            System.out.println(json.getJSONArray("value").getJSONObject(0).get("accountEnabled"));
            for (int i = 0; i < total_user_count; i++) {
                if(json.getJSONArray("value").getJSONObject(i).get("assignedLicenses").toString().length()>2){
                    licensed_user_count+=1;
                }
                if(json.getJSONArray("value").getJSONObject(i).get("accountEnabled").toString().equals("true")){
                    active_user_count+=1;
                }
            }
            int inactive_user_count =total_user_count-active_user_count;
            int unlicensed_user_count = total_user_count-licensed_user_count;
            System.out.println(total_user_count);
            System.out.println(unlicensed_user_count);
            System.out.println(licensed_user_count);
            System.out.println(inactive_user_count);
            int affectedrows = 0;
            try {
                java.sql.Connection connec = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123");
                Statement statement = connec.createStatement();
                String SQL = "update counts_table set inactive_users=?,total_users=?,licensed_users=?,unlicensed_users=? where id=1";
                PreparedStatement pstmt = connec.prepareStatement(SQL);
                {
                    pstmt.setInt(1, inactive_user_count);
                    pstmt.setInt(2, total_user_count);
                    pstmt.setInt(3, licensed_user_count);
                    pstmt.setInt(4, unlicensed_user_count);
                    affectedrows = pstmt.executeUpdate();
                }
                System.out.println(affectedrows);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    static String jsonify(String lic_no){
        String message;
        JSONObject json = new JSONObject();

        JSONArray add_arrays = new JSONArray();
        JSONArray rem_arrays = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("skuId", lic_no);
        add_arrays.put(item);

        json.put("addLicenses", add_arrays);
        json.put("removeLicenses",rem_arrays);
        String mesg;
        message = json.toString();
        mesg = message.replaceAll("\"","\\\"");
        System.out.println(mesg);
        return mesg;
    }
    static String lictok(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:49806/api/AssignLicense/")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
//            System.out.println(response.body().string());

            System.out.println(response.body());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }
    static  String lict(){
        try {
            String out =  sendGET("http://localhost:49806/api/AssignLicense/").toString();
            System.out.println(out);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
    public static boolean lic(String name, String lic_no){
        OkHttpClient client = new OkHttpClient();
        if (lic_no.equals("DEVELOPER PACK")) lic_no="189a915c-fe4f-4ffa-bde4-85b9628d07a0";
        else if (lic_no.equals("MICROSOFT FLOW")) lic_no="f30db892-07e9-47e9-837c-80727f46fd3d";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonify(lic_no));
        Request request = new Request.Builder()
                .url("https://graph.windows.net/codeninja2.onmicrosoft.com/users/"+name+"/assignLicense?api-version=1.6")
                .post(body)
                .addHeader("Authorization", lict().replaceAll("\"",""))
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
            System.out.println(response);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static StringBuilder sendGET(String GET_URL) throws IOException {

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);

            return (response);
        } else {
            return null;
        }

    }
    public static StringBuilder sendPOST(String targetURL) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");


            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.println(response);
            return response;

        } catch (Exception e) {

            e.printStackTrace();
            StringBuilder err = new StringBuilder();
            err.append(e);
            return  err;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
    public static void getDetails(String id,String tenant,String key){
        tenantAdd = tenant;
        appKey = key;
        clientID = id;
    }
    public static boolean isValidUserCredentials(String un,String pwd){
        try {
            String op = sendGET("http://localhost:49806/api/DemoLogin?username="+un+"&password="+pwd).toString();
            if(op.replaceAll("\"","").equals("1")) return true;
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static boolean checkusr(String usr){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123")) {
            PreparedStatement st = connection.prepareStatement("select * from user_details where email = ? ");
            st.setString(1, usr);
            ResultSet result = st.executeQuery();
            if(result.next()){
                return false;
            }
            st.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return false;
    }
    public static boolean sign_up(String un,String pwd){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123")) {

            Statement statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement("insert into user_details (email,password,role) values (?,?,?);");
            pstmt.setString(1, un);
            pstmt.setString(2, pwd);
            pstmt.setString(3,"user");
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return false;
    }
    public static String getRole(String un,String pwd){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123")) {

            PreparedStatement pstmt = connection.prepareStatement("select role from user_details where email = ? AND password = ?;");
            pstmt.setString(1, un);
            pstmt.setString(2, pwd);
            ResultSet result = pstmt.executeQuery();
            if (result.next()){
                return result.getString("role");
            }

        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return "failed";
    }
    public static boolean getData(String id,String add,String pass,String email){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123")) {

            PreparedStatement pstmt = connection.prepareStatement("update user_details set client_id = ?, tenant_add = ?, appkey = ? where email = ?;");
            pstmt.setString(1, id);
            pstmt.setString(2, add);
            pstmt.setString(3, pass);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            return true;

        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return false;
        }
    }
}
