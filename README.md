* In order  to run the tests - right click on the TestNGWeb.xml file and select RUN

* Tests' values can be changed in TestsProperties.properties file

* To view the report:
1. Open your IDE Terminal or CMD window
2. Verify you are located at the project folder location (or navigate to it -> cd 'your location of project'\Zim)
3. Enter the command: allure serve allure-results

The report will open on the 'Overview' page

4. Click on 'Suits' and navigate down the dropdown arrows to view the results

In case a test fails - a screenshot will be attached to the Test Body and a recording will be available under the test-recordings folder in your IDE.

Once you run a test more than once, its past runs' results will be shown under 'Retries' tab