using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Script.Serialization;

namespace Demo_API.Controllers
{
    
    public class GroupController : ApiController
    {
        public class Value
        {
            public string id { get; set; }
            public object deletedDateTime { get; set; }
            public object classification { get; set; }
            public DateTime createdDateTime { get; set; }
            public IList<object> creationOptions { get; set; }
            public string description { get; set; }
            public string displayName { get; set; }
            public IList<string> groupTypes { get; set; }
            public string mail { get; set; }
            public bool mailEnabled { get; set; }
            public string mailNickname { get; set; }
            public object onPremisesLastSyncDateTime { get; set; }
            public object onPremisesSecurityIdentifier { get; set; }
            public object onPremisesSyncEnabled { get; set; }
            public object preferredDataLocation { get; set; }
            public IList<string> proxyAddresses { get; set; }
            public DateTime renewedDateTime { get; set; }
            public IList<object> resourceBehaviorOptions { get; set; }
            public IList<object> resourceProvisioningOptions { get; set; }
            public bool securityEnabled { get; set; }
            public string visibility { get; set; }
            public IList<object> onPremisesProvisioningErrors { get; set; }
        }

        public class Root
        {
            public IList<Value> value { get; set; }
        }

        string val = OfficeLoginController.GetToken().Result;

        // GET: api/Group
        public async Task<List<string>> Get()
        {
            string user = null;
            var uri = "https://graph.microsoft.com/v1.0/groups";
            HttpClient httpClient = new HttpClient();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", val);
            var getResult = await httpClient.GetAsync(uri);
            if (getResult.Content != null)
            {
                user = await getResult.Content.ReadAsStringAsync();
            }
            List<string> res = new List<string>();
            Root op = new JavaScriptSerializer().Deserialize<Root>(user);
            foreach (var item in op.value)
            {
                List<string> output = new List<string>();
                output.Add(item.id);
                output.Add(item.displayName);
                output.Add(item.mail);
                res.AddRange(output);

            }
            return res;
        }

        // GET: api/Group/5
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Group
        public void Post([FromBody]string value)
        {
        }

        // PUT: api/Group/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Group/5
        public void Delete(int id)
        {
        }
    }
}
