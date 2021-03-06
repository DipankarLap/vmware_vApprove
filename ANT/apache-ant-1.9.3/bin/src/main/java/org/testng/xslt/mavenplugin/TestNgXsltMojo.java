package org.testng.xslt.mavenplugin;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Locale;

/**
 * @author Cosmin Marginean, Apr 1, 2008
 * @goal xslt
 */
public class TestNgXsltMojo extends AbstractMavenReport {

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter default-value="${project.reporting.outputDirectory}/testng-xslt"
     * @required
     */
    private String outputDir;

    /**
     * @parameter
     */
    private String cssFile;

    /**
     * The name of the report to use
     *
     * @parameter default-value="TestNG XSLT Results"
     */
    private String reportTitle;


    /**
     * Indicates whether to sort the test cases links in the left frame
     *
     * @parameter default-value=""
     */
    private boolean sortTestCaseLinks;

    /**
     * The description of the report
     *
     * @parameter
     */
    private String reportDescription;

    /**
     * The directory where SureFire has stored its results.
     *
     * @parameter default-value="${project.build.directory}/surefire-reports"
     */
    private String surefireReportDirectory;

    /**
     * @parameter
     */
    private boolean showRuntimeTotals;

    /**
     * @parameter
     */
    private String testDetailsFilter;

    protected void executeReport(Locale locale) throws MavenReportException {
        String testNgResultsXml = getTestNgResultsXmlPath();
        getLog().info("TestNG XSLT is processing file '" + testNgResultsXml + "'");
        if (!new File(testNgResultsXml).exists()) {
            getLog().warn("File 'testng-results.xml' could not be found. No reports will be generated by TestNG XSLT");
            return;
        }

        try {
            Thread.currentThread().setContextClassLoader(net.sf.saxon.TransformerFactoryImpl.class.getClassLoader());

            System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
            TransformerFactory factory = TransformerFactory.newInstance();
            String outputDir = getHtmlOutputDir();
            getLog().info("TestNG XSLT is generating HTML in directory '" + outputDir + "'");
            new File(outputDir).mkdirs();
            StreamSource inputSource = new StreamSource(new File(testNgResultsXml));
            Transformer transformer = factory.newTransformer(new StreamSource(getClass().getResourceAsStream("/testng-results.xsl")));

            transformer.setParameter("testNgXslt.outputDir", outputDir);
            if (cssFile != null && cssFile.trim().length() > 0) {
                transformer.setParameter("testNgXslt.cssFile", cssFile);
            }
            transformer.setParameter("testNgXslt.showRuntimeTotals", String.valueOf(showRuntimeTotals));
            transformer.setParameter("testNgXslt.reportTitle", reportTitle);
            transformer.setParameter("testNgXslt.sortTestCaseLinks", String.valueOf(sortTestCaseLinks));
            transformer.setParameter("testNgXslt.testDetailsFilter", testDetailsFilter);

            getLog().info("Transformer parameters are:");
            getLog().info("\t\ttestNgXslt.outputDir:         " + transformer.getParameter("testNgXslt.outputDir"));
            getLog().info("\t\ttestNgXslt.cssFile:           " + transformer.getParameter("testNgXslt.cssFile"));
            getLog().info("\t\ttestNgXslt.showRuntimeTotals: " + transformer.getParameter("testNgXslt.showRuntimeTotals"));
            getLog().info("\t\ttestNgXslt.reportTitle:       " + transformer.getParameter("testNgXslt.reportTitle"));
            getLog().info("\t\ttestNgXslt.sortTestCaseLinks: " + transformer.getParameter("testNgXslt.sortTestCaseLinks"));

            System.out.println("-------->>>   "=inputSource);
            transformer.transform(inputSource, new StreamResult(new FileOutputStream(outputDir + File.separator + "index.html")));
        } catch (FileNotFoundException fnfException) {
            throw new MavenReportException("An error occured during TestNG XSLT transformation", fnfException);
        } catch (TransformerConfigurationException tcException) {
            throw new MavenReportException("An error occured during TestNG XSLT transformation", tcException);
        } catch (TransformerException tException) {
            throw new MavenReportException("An error occured during TestNG XSLT transformation", tException);
        }
    }

    private String getHtmlOutputDir() {
    	String str = null;
        if (outputDir.startsWith(getProject().getBasedir().getAbsolutePath())) {
        	str = outputDir;
        } else {
        	str = getProject().getBasedir() + File.separator + getOutputDirectory();
        }
        System.out.println("--------------->>>>>>>>>>>       "+str);
        
        return str;
    }

    private String getTestNgResultsXmlPath() {
        String testNgResultsXmlPath;
        if (surefireReportDirectory.startsWith(getProject().getBasedir().getAbsolutePath())) {
            testNgResultsXmlPath = surefireReportDirectory;
        } else {
            testNgResultsXmlPath = getProject().getBasedir() + File.separator + surefireReportDirectory;
        }

        return testNgResultsXmlPath + File.separator + "testng-results.xml";
    }

    public String getDescription(Locale locale) {
        return reportDescription;
    }

    public String getName(Locale locale) {
        return reportTitle;
    }

    protected String getOutputDirectory() {
        return outputDir;
    }

    public String getOutputName() {
        final String reportingOutputDir = getProject().getReporting().getOutputDirectory();
        String outputName = getOutputDirectory() + File.separator + "index";
        if (outputName.startsWith(reportingOutputDir)) {
            outputName = outputName.substring(reportingOutputDir.length() + 1);/*also trim path sep*/
        }

        return outputName;
    }

    protected MavenProject getProject() {
        return project;
    }

    protected SiteRenderer getSiteRenderer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isExternalReport() {
        return true;
    }
}
