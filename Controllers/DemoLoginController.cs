using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Npgsql;

namespace Demo_API.Controllers
{
    public class DemoLoginController : ApiController
    {
        Demo_APIContainer1 demoapi = new Demo_APIContainer1();
        // GET: DemoLogin
        //public ActionResult Index()
        //{
        //    return View();
        //}
        [System.Web.Http.AcceptVerbs("Get", "Post")]
        [System.Web.Http.HttpGet]
        public string Login(string username, string password)
        {
            try
            {
                string connstring = "Server=127.0.0.1; Port=5432; User Id=postgres; Password=abc123; Database=postgres;";
                NpgsqlConnection connection = new NpgsqlConnection(connstring);
                connection.Open();
                NpgsqlCommand command = new NpgsqlCommand("SELECT * FROM user_details", connection);
                NpgsqlDataReader dataReader = command.ExecuteReader();
                for (int i = 0; dataReader.Read(); i++)
                {
                    if ((dataReader[1].ToString().Equals(username)) && (dataReader[2].ToString().Equals(password)))
                    {
                        return "1";
                    }
                }
                connection.Close();
                return "0";
            }
            catch (Exception msg)
            {
                return "0";

            }
        }
    }
}