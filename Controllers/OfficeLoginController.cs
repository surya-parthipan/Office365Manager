using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Threading.Tasks;
using System.Globalization;
using Microsoft.IdentityModel.Clients.ActiveDirectory;
using AuthenticationContext = Microsoft.IdentityModel.Clients.ActiveDirectory.AuthenticationContext;
using System.Net.Http.Headers;
using RestSharp;
using System.Web.Script.Serialization;

namespace Demo_API.Controllers
{
    public class OfficeLoginController : ApiController
    {
        public class RootObject
        {
            public string token_type { get; set; }
            public string expires_in { get; set; }
            public string ext_expires_in { get; set; }
            public string expires_on { get; set; }
            public string not_before { get; set; }
            public string resource { get; set; }
            public string access_token { get; set; }
        }

        const string clientId = "1daab759-0da4-41c6-9cc0-9a7e28eb385a";
        const string tenantadd = "codeninja2.onmicrosoft.com";
        const string resource = "https://graph.microsoft.com";
        const string username = "parthipan@codeninja2.onmicrosoft.com";
        const string password = "10212450@sp";
               
        public static async Task<string> GetToken()
        {
            var client = new RestClient("https://login.windows.net/codeninja2.onmicrosoft.com/oauth2/token");
            var request = new RestRequest(Method.POST);
            request.AddHeader("Postman-Token", "dc13b129-a061-4ae7-afc6-9f58f24e083b");
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("undefined", "client_id="+clientId+"&scope=openid&redirect_uri=https%3A%2F%2F"+tenantadd+"&grant_type=password&resource=https%3A%2F%2Fgraph.microsoft.com&username="+username+"&password="+password, ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            List<string> res = new List<string>();
            RootObject op = new JavaScriptSerializer().Deserialize<RootObject>(content);
            return op.access_token;
        }
        private static async Task<string> GetAD(string tokenValue)
        {
            var client = new RestClient("https://login.microsoftonline.com/codeninja2.onmicrosoft.com/oauth2/token");
            var request = new RestRequest(Method.POST);
            request.AddHeader("Postman-Token", "b84218b1-ab9c-47ab-861b-5eef0cc0b264");
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("undefined", "client_id=1daab759-0da4-41c6-9cc0-9a7e28eb385a&scope=openid&redirect_uri=https%3A%2F%2Fcodeninja2.onmicrosoft.com&grant_type=password&resource=https%3A%2F%2Fgraph.windows.net&username=parthipan%40codeninja2.onmicrosoft.com&password=10212450%40sp", ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            List<string> res = new List<string>();
            RootObject op = new JavaScriptSerializer().Deserialize<RootObject>(content);
            return op.access_token;
        }

        // GET: api/OfficeLogin
        public string Get()
        {
            try
            {
                Task<string> token = GetToken();
                return token.Result;
            }
            catch
            {
                return "invalid Access to the system";
            }    
        }

        
    }
}
