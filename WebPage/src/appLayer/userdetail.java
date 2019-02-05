package appLayer;
import com.google.gson.JsonObject;
import okhttp3.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class userdetail {

    public  String jsonify(String name,String nickname,String pass,String loc){
        JsonObject person = new JsonObject();
        person.addProperty("accountEnabled", true);
        person.addProperty("displayName", name);
        person.addProperty("mailNickname", nickname);
        person.addProperty("usageLocation", loc);
        JsonObject passwordProfile = new JsonObject();
        passwordProfile.addProperty("password", pass);
        passwordProfile.addProperty("forceChangePasswordNextSignIn", false);
        person.add("passwordProfile", passwordProfile);
        person.addProperty("userPrincipalName", nickname+"@codeninja2.onmicrosoft.com");

        System.out.println(person.toString());
        return person.toString();
    }
    public  boolean createUSer(String name,String nickname,String pass,String loc){
        String token = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:49806/api/OfficeLogin")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "b2badab3-bf4d-4118-a23f-07db9b158d0d")
                    .build();

            Response response = client.newCall(request).execute();
            token = response.body().string();

            System.out.println(token);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonify(name,nickname,pass,loc));
            Request requests = new Request.Builder()
                    .url("https://graph.microsoft.com/v1.0/users")
                    .post(body)
                    .addHeader("Authorization", token.replaceAll("\"",""))
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response responses = client.newCall(requests).execute();
                System.out.println(responses);
                if(responses.message().replaceAll("\"","").equals("Created")){
                    return true;
                }
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public  String jsonifypass(String name){
        JsonObject person = new JsonObject();
        JsonObject passwordProfile = new JsonObject();
        passwordProfile.addProperty("password", name);
        passwordProfile.addProperty("forceChangePasswordNextSignIn", false);
        person.add("passwordProfile", passwordProfile);

        System.out.println(person.toString());
        return person.toString();
    }
    public  boolean resetpass(String name,String id){
        String token = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:49806/api/OfficeLogin")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "b2badab3-bf4d-4118-a23f-07db9b158d0d")
                    .build();

            Response response = client.newCall(request).execute();
            token = response.body().string();

            System.out.println(token);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, jsonifypass(name));
            Request requests = new Request.Builder()
                    .url("https://graph.microsoft.com/v1.0/users/"+id+"@codeninja2.onmicrosoft.com")
                    .patch(body)
                    .addHeader("Authorization", token.replaceAll("\"",""))
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response responses = client.newCall(requests).execute();
                if(responses.message().replaceAll("\"","").equals("Bad Request")){
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deluser(String id){
        String token = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:49806/api/OfficeLogin")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "b2badab3-bf4d-4118-a23f-07db9b158d0d")
                    .build();

            Response response = client.newCall(request).execute();
            token = response.body().string();

            System.out.println(token);
            try {
                MediaType mediaType = MediaType.parse("application/json");
                Request requests = new Request.Builder()
                        .url("https://graph.microsoft.com/beta/users/"+id)
                        .delete(null)
                        .addHeader("Authorization", token.replaceAll("\"",""))
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response respons = client.newCall(requests).execute();
                if(respons.message().replaceAll("\"","").equals("Bad Request")){
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public  String jsonifyblock(){
        JsonObject person = new JsonObject();
        person.addProperty("accountEnabled", false);
        System.out.println(person.toString());
        return person.toString();
    }
    public  String jsonifyactive(){
        JsonObject person = new JsonObject();
        person.addProperty("accountEnabled", true);
        System.out.println(person.toString());
        return person.toString();
    }
    public  boolean blockuser(String name){
        String token = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:49806/api/OfficeLogin")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "b2badab3-bf4d-4118-a23f-07db9b158d0d")
                    .build();

            Response response = client.newCall(request).execute();
            token = response.body().string();

            System.out.println(token);
            try {
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonifyblock());
                Request requests = new Request.Builder()
                        .url("https://graph.microsoft.com/v1.0/users/"+name)
                        .patch(body)
                        .addHeader("Authorization", token.replaceAll("\"",""))
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response responses = client.newCall(requests).execute();
                if(responses.message().replaceAll("\"","").equals("Bad Request")){
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public  boolean activeuser(String name){
        String token = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:49806/api/OfficeLogin")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "b2badab3-bf4d-4118-a23f-07db9b158d0d")
                    .build();

            Response response = client.newCall(request).execute();
            token = response.body().string();

            System.out.println(token);
            try {
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, jsonifyactive());
                Request requests = new Request.Builder()
                        .url("https://graph.microsoft.com/v1.0/users/"+name)
                        .patch(body)
                        .addHeader("Authorization", token.replaceAll("\"",""))
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response responses = client.newCall(requests).execute();
                if(responses.message().replaceAll("\"","").equals("Bad Request")){
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public  String cnt(){
        String cts = "asd";
        try {
            java.sql.Connection connec = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "abc123");
            Statement statement = connec.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM counts_table where id = 1");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("inactive_users"));
                cts +=(resultSet.getInt("licensed_users"));
                cts +=(resultSet.getInt("total_users"));
                cts +=(resultSet.getInt("unlicensed_users"));
                return(cts);
            }
            System.out.println(cts);

        } catch (SQLException e) {
            e.printStackTrace();
            return (cts);
        }
        return (cts);
    }
}
