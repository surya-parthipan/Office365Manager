$(function() {


    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Total Users",
            value: "<%= usr_cnt %>"
        }, {
            label: "No.Of Inactive Users",
            value: "<%= inact_usr %>"
        }, {
            label: "No.Of Licensed Users",
            value: "<%= lic_usr %>"
        }, {
            label: "No.Of UnLicensed Users",
            value: "<%= unlic_usr %>"
        }],
        resize: true
    });

});