https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image1.png">

*“Always be aware of JIRA issues for your favorite Project(s) via SMS”*

https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image2.png">

## Introduction

**JIRA with Nexmo SMS** add-on provides extended notification feature in JIRA. JIRA users can receive notification on their mobile via SMS. JIRA with Nexmo SMS add-on enables JIRA to send SMS notifications to JIRA users (issue assignee and reporter) when a new issue is assigned, resolved or reopened. JIRA administrator can configure JIRA projects with issue types and issue priorities in which SMS notification is required, and also the admin can enable and disable selected events like issue assigned, issue resolved or issue reopened of JIRA with Nexmo SMS add-on.

## Use case

Build add-on for JIRA to send SMS notifications to the assignee and reporter for an issue of a specific project with issue type and issue priority, whenever any new issue is assigned, resolved or reopened.

For example - any new issue is created and assigned in XYZ Project, the add-on will send an SMS to the User X who is the assignee of that issue.

-   Issue assigned

    -   Hi &lt;assignee\_name&gt;, a new &lt;IssueType&gt; &lt;IssueName&gt; is assigned to you, reported by &lt;reporter name&gt;.

-   Issue resolved

    -   Hi &lt;reporter\_name&gt;, the &lt;IssueType&gt; &lt;IssueName&gt; reported by you is resolved by &lt;assignee name&gt;.

-   Issue reopened

    -   Hi &lt;assignee\_name&gt;, the &lt;IssueType&gt; &lt;IssueName&gt; is reopened and assigned to you by &lt;reporter name&gt;.

Prerequisites 
==============

-   Preinstalled and running JIRA server hosted on server.

-   JIRA user with administrative privileges.

-   Nexmo subscription and corresponding Nexmo API keys (Keys and Secret). To access the Nexmo API keys, see appendix section.

Features 
=========

-   Sends SMS to the issue assignee and reporter.

-   Configures selected projects along with the issue type and priority, which require SMS notification.

-   Enables and disables SMS functionality for the JIRA platform.

-   Allows users to enable and disable the SMS feature for individual events such as **Issue Assign**, **Issue Resolved** and **Issue Reopened** events

-   This add-on fully supports the JIRA workflow and classic default JIRA workflow; some other work flows are partially supported.

Steps to install JIRA with Nexmo SMS add-on
===========================================

1.  Visit the target Git repository using the URL - https://github.com/advaiyalabs/

2.  Click on **Download ZIP** as shown in the below image:https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image3.png">

3.  Extract ZIP file.

4.  Login with admin user on JIRA server.

5.  Navigate to **Settings**-&gt;**Add-ons** as shown in the image below:

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image4.PNg">

6.  Click on **Upload add-on** in Add-ons tab

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image5.PNg">

7.  Click on **Choose File** and select the **JIRA with Nexmo SMS-1.0** file from the folder where you have unzipped the downloaded file Jira with Nexmo SMS-1.0

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image6.PNg">

8.  Click on **Upload** to start the upload process.

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image7.png">

9.  On **Installed and ready to go!** screen, click on **Close** to continue.

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image8.PNg">

10. In the list of user-installed add-ons, newly installed add-on **JIRA with Nexmo SMS** is available.

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image9.PNg">

11. On Jira administration screen, under the **Manage add-ons** section, click on **Nexmo Configuration** option in the side bar**.**

    https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image10.PNg">

12. On Nexmo configuration page, provide the following inputs:

    1.  Set Nexmo Key and Nexmo Secret.

        https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image11.png">

    2.  Click on **Validate.** When **Nexmo Key** and **Secret** validation is done successfully, you will see the remaining additional configuration settings as follows:

        1.  Select **From Number** which is used to send messages. (these are virtual numbers you subscribed from Nexmo)

            https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image12.PNg">

        2.  Select specific projects from **All Projects**, multiple projects can be selected.

        3.  Select Issue Types of selected projects.

        4.  Select Issue Priorities; select only in which SMS notification are required.

        5.  In Jira, by default there is no field for Phone Number, so you have to create a custom Phone Number field. If you have already created such field, you will have to choose it from the **From Number** dropdown.

            https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image13.PNg">

13. To enable JIRA with Nexmo SMS feature, select the check box **Enable SMS** and select the check box of respective Issue Events (Issue Assigned, Issue Resolved and Issue Reopened)

14. Click on **Save** to complete the add-on installation and save all the settings.

<span id="_Toc432770621" class="anchor"><span id="_Toc439939548" class="anchor"></span></span>Steps to use JIRA with Nexmo SMS add-on
=====================================================================================================================================

JIRA users (assignee and reporter) will receive an SMS when a new issue is assigned, issue is resolved or reopened.

1.  Create a new issue with your configured project and other settings of JIRA with Nexmo SMS.

2.  To enable SMS feature, ask your users to add phone numbers in their profile along with the country code.

3.  To receive an SMS, issue must be created in the configured project with selected issue type and priority

<span id="_Toc432770622" class="anchor"></span>

Appendix
========

<span id="_Toc432770623" class="anchor"><span id="_Toc439939550" class="anchor"></span></span>Nexmo API Keys
------------------------------------------------------------------------------------------------------------

-   To access Nexmo keys, go to <https://www.nexmo.com/> and sign-in

-   On the top right corner, click on the **Api Settings**

-   Key and Secret will display in the top bar as shown in the below image:

> https://github.com/AdvaiyaLabs/JIRA-with-Nexmo-SMS/blob/master/media/image14.png">
