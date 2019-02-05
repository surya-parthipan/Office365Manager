package WebPage;
import java.util.List;

public class Response {
    public class Value
    {
        public String id;
        public List<String> businessPhones;
        public String displayName;
        public String givenName;
        public String jobTitle;
        public String mail;
        public String mobilePhone;
        public String officeLocation;
        public String preferredLanguage;
        public String surname;
        public String userPrincipalName;
    }

    public class data
    {
        public List<Value> value;
    }
}
