AJS.toInit(function() {

    var baseUrl = AJS.$("meta[name='jira-base-url']").attr("content");

    AJS.$("#upm-incompatible-plugins-msg").hide();
    AJS.$("#aftervalidation").hide();

    function populateForm() {



        AJS.$("#progressSpinner").show();
        AJS.$("#progressText").text("Getting configuration");
        AJS.$("#upm-incompatible-plugins-msg").hide();

        AJS.$.ajax({
            url: AJS.params.baseURL + "/rest/nexmo-admin/1.0/",
            dataType: "json",
            success: function(config) {
                //success start


                AJS.$("#progressSpinner").hide();
                AJS.$("#key").attr("value", config.key);
                AJS.$("#secret").attr("value", config.secret);

                if (config.fromuser == "ERROR") {

                    AJS.$("#aftervalidation").hide();
                    AJS.$("#upm-incompatible-plugins-msg").show();
                    AJS.$("#validatekeys").show();
                    AJS.$("#error").text("Please enter valid Nexmo Key and Secret.");

                    // alert("Please enter valid Nexmo Key and Secret.");


                } else {
                    AJS.$("#aftervalidation").show();
                    AJS.$("#validatekeys").hide();
                }
                AJS.$("#smsenable").attr("value", config.smsenable);



                if (config.smsenable == 1) {
                    AJS.$("#smsenable").attr('checked', true);
                    AJS.$("#eventspanel :input").removeAttr('disabled');

                } else {
                    AJS.$("#smsenable").attr('checked', false);
                    AJS.$("#eventspanel :input").attr('disabled', true);
                }




                if (config.eventissueassigned == 1) {
                    AJS.$("#issueassigned").attr('checked', true);

                } else {

                    AJS.$("#issueassigned").attr('checked', false);
                }


                if (config.eventissueresolved == 1) {
                    AJS.$("#issueresolved").attr('checked', true);

                } else {

                    AJS.$("#issueresolved").attr('checked', false);
                }

                if (config.eventissuereopened == 1) {
                    AJS.$("#issuereopened").attr('checked', true);

                } else {

                    AJS.$("#issuereopened").attr('checked', false);
                }



                AJS.$("#phone").attr("value", config.phonefield);

                var froms = config.fromuser;
                var allfroms = froms.split(';');
                AJS.$('#fromNumbers')
                    .empty();


                AJS.$("#fromuser").attr("value", config.fromuserDefault);

                for (i = 0; i < allfroms.length - 1; i++) {

                    var allNum = allfroms[i].split(':');
                    var str = allNum[0] + "   (" + allNum[1] + ")"




                    if (allNum[0] == config.fromuserDefault) {


                        AJS.$('<option/>', {
                            'text': str,
                            'value': allNum[0],
                            'selected': 'selected'
                        }).appendTo('#fromNumbers');


                    } else {

                        AJS.$('<option/>', {
                            'text': str,
                            'value': allNum[0],

                        }).appendTo('#fromNumbers');


                    }


                }




                AJS.$("#fromNumbers select").val(config.fromuserDefault);

                var sel = config.selectedprojects;

                var arr = sel.split(';');

                var selProjects = "";
                AJS.$("#listprojects > option").each(function() {

                    for (i = 0; i < arr.length - 1; i++) {

                        if (arr[i] == this.value) {

                            AJS.$(this).attr("selected", true);
                            selProjects += this.text + " ,";
                        }
                    }

                });

                if (selProjects == "") {
                    AJS.$("#selprojects").text("All Projects");

                } else {
                    selProjects = selProjects.substring(0, selProjects.length - 1);
                    AJS.$("#selprojects").text("All Projects : " + selProjects);

                }

                var seltypes = config.selectedissuetypes;
                var arrtypes = seltypes.split(';');
                var selIssueType = "";
                AJS.$("#issuetype > option").each(function() {


                    for (i = 0; i < arrtypes.length - 1; i++) {

                        if (arrtypes[i] == this.value) {
                            AJS.$(this).attr("selected", true);
                            selIssueType += this.text+" ,";
                        }
                    }

                });
                if (selIssueType == "") {
                    AJS.$("#issuetypesel").text("Issue Type");

                } else {
                    selIssueType = selIssueType.substring(0, selIssueType.length - 1);
                    AJS.$("#issuetypesel").text("Issue Type : " + selIssueType);

                }


                var selprio = config.selectedpriorites;
                var arrprio = selprio.split(';');

                var selPriority = "";
                AJS.$("#issuepriority > option").each(function() {


                    for (i = 0; i < arrprio.length - 1; i++) {

                        if (arrprio[i] == this.value) {
                            AJS.$(this).attr("selected", true);
                            selPriority += this.text+" ,";
                        }
                    }

                });
                if (selPriority == "") {
                    AJS.$("#issuepriosel").text("Issue Priority");

                } else {
                    selPriority = selPriority.substring(0, selPriority.length - 1);
                    AJS.$("#issuepriosel").text("Issue Priorities : " + selPriority);

                }

                //success end       
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("Could not retrieve configuration: " + textStatus);
                AJS.$("#progressSpinner").hide();
            }
        });
    }

    function validateKeys() {


        var key = AJS.$.trim(AJS.$("#key").val());
        var secret = AJS.$.trim(AJS.$("#secret").val());

        if (key == "") {

            AJS.$("#error").text("Please enter the Nexmo Key");
            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#key").focus();
            return;
        }
        if (secret == "") {

            AJS.$("#error").text("Please enter the Nexmo Secret");
            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#secret").focus();
            return;
        }

        var smsenable_ = "0";
        var issueassigned = "0";
        var issueresolved = "0";
        var issuereopened = "0";

        if (AJS.$("#smsenable").is(":checked")) {
            smsenable_ = "1";

        } else {


            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#error").text("SMS will not be sent to the contact list");
            AJS.$('#smsenable').focus();
        }

        if (AJS.$("#issueassigned").is(":checked")) {
            issueassigned = "1";
        }

        if (AJS.$("#issueresolved").is(":checked")) {
            issueresolved = "1";
        }
        if (AJS.$("#issuereopened").is(":checked")) {
            issuereopened = "1";
        }



        var defaultFromNumber = AJS.$('#fromNumbers').find(":selected").val();

        var datas = '{ "key": "' + AJS.$("#key").attr("value") + '","secret": "' + AJS.$("#secret").attr("value") + '","fromuserDefault": "' + defaultFromNumber + '","smsenable": "' + smsenable_ + '","eventissueassigned": "' + issueassigned + '","eventissueresolved": "' + issueresolved + '","eventissuereopened": "' + issuereopened + '"}';
        AJS.$("#progressSpinner").show();
        AJS.$("#progressText").text("Please wait...");
        AJS.$.ajax({
            url: AJS.params.baseURL + "/rest/nexmo-admin/1.0/",
            type: "PUT",
            contentType: "application/json",
            data: datas,
            processData: false,
            success: function(config) {



                config = config.replace(/(\r\n|\n|\r)/gm, "");
                if (config == "ERROR") {

                    // alert("Please enter valid Nexmo Key and Secret.");
                    AJS.$("#progressSpinner").hide();
                    AJS.$("#aftervalidation").hide();
                    AJS.$("#validatekeys").show();
                    AJS.$("#upm-incompatible-plugins-msg").show();
                    AJS.$("#error").text("Please enter valid Nexmo Key and Secret.");
                } else {

                    var froms = config;
                    var allfroms = froms.split(';');
                    AJS.$("#fromNumbers").empty();
                    AJS.$("#progressSpinner").hide();


                    for (i = 0; i < allfroms.length - 1; i++) {

                        var allNum = allfroms[i].split(':');
                        var str = allNum[0] + "   (" + allNum[1] + ")"



                        AJS.$('<option/>', {
                            'text': str,
                            'value': allNum[0],

                        }).appendTo("#fromNumbers");



                    }
                    AJS.$("#upm-incompatible-plugins-msg").hide();
                    AJS.$("#aftervalidation").show();
                    //alert("Configuration settings saved successfully.");   
                }



            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                AJS.$("#progressSpinner").hide();

                AJS.$("#error").text("Please enter valid Nexmo Key and Secret.");
                AJS.$("#aftervalidation").hide();
                AJS.$("#upm-incompatible-plugins-msg").show();

            }
        });

    }



    function SaveData() {


        var key = AJS.$.trim(AJS.$("#key").val());
        var secret = AJS.$.trim(AJS.$("#secret").val());

        if (key == "") {

            AJS.$("#error").text("Please enter the Nexmo Key");
            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#key").focus();
            return;
        }
        if (secret == "") {

            AJS.$("#error").text("Please enter the Nexmo Secret");
            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#secret").focus();
            return;
        }



        var smsenable_ = "0";
        var issueassigned = "0";
        var issueresolved = "0";
        var issuereopened = "0";



        if (AJS.$("#smsenable").is(":checked")) {
            smsenable_ = "1";

        } else {


            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#error").text("SMS will not be sent to the contact list");
            AJS.$('#smsenable').focus();
        }

        if (AJS.$("#issueassigned").is(":checked")) {
            issueassigned = "1";
        }

        if (AJS.$("#issueresolved").is(":checked")) {
            issueresolved = "1";
        }
        if (AJS.$("#issuereopened").is(":checked")) {
            issuereopened = "1";
        }

        var selectedProjects = "";
        AJS.$("#listprojects > option:selected").each(function(item) {

            if (AJS.$(this).val() != "") {
                selectedProjects += AJS.$(this).val() + ";";
            }
        });

        var selectedTypes = "";
        AJS.$("#issuetype > option:selected").each(function(item) {

            if (AJS.$(this).val() != "") {
                selectedTypes += AJS.$(this).val() + ";";
            }
        });
        var selectedPriorites = "";
        AJS.$("#issuepriority > option:selected").each(function(item) {

            if (AJS.$(this).val() != "") {
                selectedPriorites += AJS.$(this).val() + ";";
            }
        });


        var phone = AJS.$.trim(AJS.$("#phone").val());
        if (phone == "") {

            AJS.$("#error").text("Please enter the Phone Field Name(Key)");
            AJS.$("#upm-incompatible-plugins-msg").show();
            AJS.$("#phone").focus();
            return;
        }


        AJS.$("#upm-incompatible-plugins-msg").hide();

        var defaultFromNumber = AJS.$('#fromNumbers').find(":selected").val();

        var datas = '{ "key": "' + AJS.$("#key").attr("value") + '","secret": "' + AJS.$("#secret").attr("value") + '","fromuserDefault": "' + defaultFromNumber + '","smsenable": "' + smsenable_ + '","eventissueassigned": "' + issueassigned + '","eventissueresolved": "' + issueresolved + '","eventissuereopened": "' + issuereopened + '","selectedprojects": "' + selectedProjects + '","selectedissuetypes": "' + selectedTypes + '","selectedpriorites": "' + selectedPriorites + '","phonefield":"' + phone + '"}';
        AJS.$("#progressSpinner").show();
        AJS.$("#progressText").text("Saving configuration");

        AJS.$.ajax({
            url: AJS.params.baseURL + "/rest/nexmo-admin/1.0/",
            type: "PUT",
            contentType: "application/json",
            data: datas,
            processData: false,
            success: function(config) {

                AJS.$("#progressSpinner").hide();
                config = config.replace(/(\r\n|\n|\r)/gm, "");
                if (config == "ERROR") {

                    // alert("Please enter valid Nexmo Key and Secret.");

                    AJS.$("#aftervalidation").hide();
                    AJS.$("#validatekeys").show();
                    AJS.$("#upm-incompatible-plugins-msg").show();
                    AJS.$("#error").text("Please enter valid Nexmo Key and Secret.");

                } else {

                    AJS.$("#upm-incompatible-plugins-msg").hide();
                    AJS.$("#aftervalidation").show();
                    populateForm();

                    alert("Configuration settings saved successfully.");
                }



            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                AJS.$("#progressSpinner").hide();
                //	  alert("Failed to save configuration");	
                AJS.$("#error").text("Please enter valid Nexmo Key and Secret.");
                AJS.$("#aftervalidation").hide();
                AJS.$("#upm-incompatible-plugins-msg").show();

            }
        });

    }


    populateForm();


    AJS.$("#issuepriority")
        .change(function() {

            var str = "";

            AJS.$("#issuepriority > option:selected").each(function(item) {

                if (AJS.$(this).val() != "") {
                    str += AJS.$(this).val() + " ,";
                }
            });
            if (str == "") {
                AJS.$("#issuepriosel").text("Issue Priority");
            } else {
            
                str = str.substring(0, str.length - 1);
                AJS.$("#issuepriosel").text("Issue Priorities : " + str);
            }

        });


    AJS.$("#listprojects")
        .change(function() {

            var str = "";
            AJS.$("#listprojects > option:selected").each(function(item) {

                if (AJS.$(this).val() != "") {
                    str += AJS.$(this).text() + " ,";
                }
            });

            if (str == "") {
                AJS.$("#selprojects").text("All Projects");

            } else {
                str = str.substring(0, str.length - 1);
                AJS.$("#selprojects").text("All Projects : " + str);

            }

        });


    AJS.$("#issuetype")
        .change(function() {

            var str = "";

            AJS.$("#issuetype > option:selected").each(function(item) {

                if (AJS.$(this).val() != "") {
                    str += AJS.$(this).val() + " ,";
                }
            });


            if (str == "") {
                AJS.$("#issuetypesel").text("Issue Type");

            } else {
                str = str.substring(0, str.length - 1);
                AJS.$("#issuetypesel").text("Issue Type : " + str);

            }

        });

    AJS.$("#smsenable").click(function() {




        if (AJS.$("#smsenable").is(":checked")) {
            AJS.$("#eventspanel  :input").removeAttr('disabled');
        } else {

            AJS.$("#eventspanel :input").attr('disabled', true);
        }

    });



    AJS.$("#validatekeys").click(function(e) {
        e.preventDefault();
        validateKeys();
    });

    AJS.$("#savedata").click(function(e) {
        e.preventDefault();
        SaveData();
    });


});