using Microsoft.IdentityModel.Clients.ActiveDirectory;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Script.Serialization;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Mvc.Filters;
using AuthenticationContext = Microsoft.IdentityModel.Clients.ActiveDirectory.AuthenticationContext;

namespace Demo_API.Controllers
{
    public class AssignLicenseController : ApiController
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
        const string resource = "https://graph.windows.net";
        const string username = "parthipan@codeninja2.onmicrosoft.com";
        const string password = "10212450@sp";

        public static async Task<string> GetADToken()
        {
            var client = new RestClient("https://login.microsoftonline.com/codeninja2.onmicrosoft.com/oauth2/token");
            var request = new RestRequest(Method.POST);
            request.AddHeader("Postman-Token", "ad065d1b-b66e-43a8-b11f-90c2b0de41f8");
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("undefined", "client_id=" + clientId + "&scope=openid&redirect_uri=https%3A%2F%2F" + tenantadd + "&grant_type=password&resource=https%3A%2F%2Fgraph.windows.net&username=" + username + "&password=" + password, ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            List<string> res = new List<string>();
            RootObject op = new JavaScriptSerializer().Deserialize<RootObject>(content);
            return op.access_token;
        }

        

        // GET: api/AssignLicense
        public string Get()
        {
            try
            {
                Task<string> token = GetADToken();
                return token.Result;
            }
            catch
            {
                return "invalid Access to the system";
            }
        }
        // GET: api/AssignLicense/5
        public string Get(string id)
        {
            var client = new RestClient("https://graph.windows.net/codeninja2.onmicrosoft.com/users/test3@codeninja2.onmicrosoft.com/assignLicense?api-version=1.6");
            Task<string> token = GetADToken();
            var tokenVal = token.Result;
            var request = new RestRequest(Method.POST);
            request.AddHeader("Postman-Token", "00541fb0-cba0-4c83-a82f-091b8027c5d3");
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("Content-Type", "application/json");
            request.AddHeader("Authorization", tokenVal);
            request.AddParameter("undefined", "{\r\n  \"addLicenses\":[{\"disabledPlans\":[ ],\"skuId\":\"f30db892-07e9-47e9-837c-80727f46fd3d\"}],\r\n  \"removeLicenses\":[ ]\r\n}", ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
            return response.Content;
        }

        // POST: api/AssignLicense
        public void Post([FromBody]string value)
        {
        }

        // PUT: api/AssignLicense/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/AssignLicense/5
        public void Delete(int id)
        {
        }
    }
}
