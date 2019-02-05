using Microsoft.IdentityModel.Clients.ActiveDirectory;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Script.Serialization;
using System.Web.Mvc.Filters;
using AuthenticationContext = Microsoft.IdentityModel.Clients.ActiveDirectory.AuthenticationContext;
using Newtonsoft.Json;
using System.Dynamic;
using System.Text.RegularExpressions;
using System.Text;
using Demo_API.Controllers;
using RestSharp;

namespace Demo_API.Controllers
{
    public class AssignedLicens
    {
        public IList<object> disabledPlans { get; set; }
        public string skuId { get; set; }
    }

    public class AssignedPlan
    {
        public DateTime assignedTimestamp { get; set; }
        public string capabilityStatus { get; set; }
        public string service { get; set; }
        public string servicePlanId { get; set; }
    }

    public class PasswordProfile
    {
        public object password { get; set; }
        public bool forceChangePasswordNextLogin { get; set; }
        public bool enforceChangePasswordPolicy { get; set; }
    }

    public class ProvisionedPlan
    {
        public string capabilityStatus { get; set; }
        public string provisioningStatus { get; set; }
        public string service { get; set; }
    }

    public class Value
    {
        public string objectType { get; set; }
        public string objectId { get; set; }
        public object deletionTimestamp { get; set; }
        public bool accountEnabled { get; set; }
        public object ageGroup { get; set; }
        public IList<AssignedLicens> assignedLicenses { get; set; }
        public IList<AssignedPlan> assignedPlans { get; set; }
        public string city { get; set; }
        public object companyName { get; set; }
        public object consentProvidedForMinor { get; set; }
        public string country { get; set; }
        public DateTime createdDateTime { get; set; }
        public object creationType { get; set; }
        public string department { get; set; }
        public object dirSyncEnabled { get; set; }
        public string displayName { get; set; }
        public object employeeId { get; set; }
        public object facsimileTelephoneNumber { get; set; }
        public string givenName { get; set; }
        public object immutableId { get; set; }
        public object isCompromised { get; set; }
        public string jobTitle { get; set; }
        public object lastDirSyncTime { get; set; }
        public object legalAgeGroupClassification { get; set; }
        public string mail { get; set; }
        public string mailNickname { get; set; }
        public string mobile { get; set; }
        public object onPremisesDistinguishedName { get; set; }
        public object onPremisesSecurityIdentifier { get; set; }
        public IList<string> otherMails { get; set; }
        public string passwordPolicies { get; set; }
        public PasswordProfile passwordProfile { get; set; }
        public string physicalDeliveryOfficeName { get; set; }
        public string postalCode { get; set; }
        public string preferredLanguage { get; set; }
        public IList<ProvisionedPlan> provisionedPlans { get; set; }
        public IList<object> provisioningErrors { get; set; }
        public IList<string> proxyAddresses { get; set; }
        public DateTime refreshTokensValidFromDateTime { get; set; }
        public object showInAddressList { get; set; }
        public IList<object> signInNames { get; set; }
        public string sipProxyAddress { get; set; }
        public string state { get; set; }
        public string streetAddress { get; set; }
        public string surname { get; set; }
        public string telephoneNumber { get; set; }
        public string usageLocation { get; set; }
        public IList<object> userIdentities { get; set; }
        public string userPrincipalName { get; set; }
        public object userState { get; set; }
        public object userStateChangedOn { get; set; }
        public string userType { get; set; }
}

    public class RootObject
    {
        public IList<Value> value { get; set; }
    }


public class UsersController : ApiController
    {
        string val = AssignLicenseController.GetADToken().Result;
        
        // GET: api/Users
        public async Task<List<string>> Get()
        {
            var client = new RestClient("https://graph.windows.net/myorganization/users");
            var request = new RestRequest(Method.GET);
            request.AddHeader("Postman-Token", "01237162-162d-4759-8de4-33fdce5a1250");
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("api-Version", "1.6");
            request.AddHeader("Authorization",val );
            IRestResponse response = client.Execute(request);
            var content = response.Content;
            List<string> res = new List<string>();
            RootObject op = new JavaScriptSerializer().Deserialize<RootObject>(content);     
            foreach (var item in op.value)
            {
                List<string> output = new List<string>();
                output.Add(item.objectId);
                output.Add(item.displayName);
                output.Add(item.userPrincipalName);
                if (item.accountEnabled)
                {
                    output.Add("Active");
                }
                else
                {
                    output.Add("Blocked");
                }
                res.AddRange(output);    
            }
            return res;
        }
        [Route("api/Users/{id}")]
        // GET: api/Users/5
        public async Task<List<string>> GetByMail(string id)
        {
            string user = null;
            var uri = "https://graph.windows.net/myorganization/users/" + id + "@codeninja2.onmicrosoft.com";
            HttpClient httpClient = new HttpClient();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", val);
            var getResult = await httpClient.GetAsync(uri);
            if (getResult.Content != null)
            {
                user = await getResult.Content.ReadAsStringAsync();
            }
            List<string> res = new List<string>();
            Value op = new JavaScriptSerializer().Deserialize<Value>(user);
            if(true)
            {
                List<string> output = new List<string>();
                res.Add(op.objectId);
                res.Add(op.displayName);
                res.Add(op.mail);
            }
            return res;
        }

       
        // PUT: api/Users/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Users/5
        public void Delete(int id)
        {
        }


    }
}
