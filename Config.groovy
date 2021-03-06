outputPath = 'build/docs'

// Path where the docToolchain will search for the input files.
// This path is appended to the docDir property specified in gradle.properties
// or in the command line, and therefore must be relative to it.
inputPath = 'src/docs'
pdfThemeDir = './src/docs/pdfTheme'

inputFiles = [
        [file: '*.adoc',            formats: ['html','pdf']],
        [file: 'arc42/arc42.adoc', formats: ['html','pdf']],
]

//folders in which asciidoc will find images.
//these will be copied as resources to ./images
//folders are relative to inputPath
imageDirs = [
        'images/.',
        /** imageDirs **/
]

// these are directories (dirs) and files which Gradle monitors for a change
// in order to decide if the docs have to be re-build
taskInputsDirs = [
        "${inputPath}",
//      "${inputPath}/src",
//      "${inputPath}/images",
]

taskInputsFiles = []

//Configuration for microsite: generateSite + previewSite

microsite = [:]

// these properties will be set as jBake properties
// microsite.foo will be site.foo in jBake and can be used as config.site_foo in a template
// see https://jbake.org/docs/2.6.4/#configuration for how to configure jBake
// other properties listed here might be used in the jBake templates and thus are not
// documented in the jBake docs but hopefully in the template docs.
microsite.with {
    /** start:microsite **/

    // is your microsite deployed with a context path?
    contextPath = '/'
    // used as title in the template
    title='Microsite'
    // used in the template for absolute uris
    host='https://localhost'
    // configure a port on which your preview server will run
    previewPort = 8881
    // the folder of a site definition (theme) relative to the docDir+inputPath
    //siteFolder = '../site'

    /** end:microsite **/

    //project theme
    //site folder relative to the docs folder
    //see 'copyTheme' for more details
    siteFolder = '../site'

    // the title of the microsite, displayed in the upper left corner
    title = '##site-title##'
    // the next items configure some links in the footer
    //
    // contact eMail
    // example: mailto:bert@example.com
    footerMail = '##footer-email##'
    //
    // twitter account url
    footerTwitter = '##twitter-url##'
    //
    // Stackoverflow QA
    footerSO = '##Stackoverflow-url##'
    //
    // Github Repository
    footerGithub = '##Github-url##'
    //
    // Slack Channel
    footerSlack = '##Slack-url##'
    //
    // Footer Text
    // example: <small class="text-white">built with docToolchain and jBake <br /> theme: docsy</small>
    footerText = '<small class="text-white">built with <a href="https://doctoolchain.org">docToolchain</a> and <a href="https://jbake.org">jBake</a> <br /> theme: <a href="https://www.docsy.dev/">docsy</a></small>'
    //
    // site title if no other title is given
    title = 'docToolchain'
    //
    // the url to create an issue in github
    // Example: https://github.com/docToolchain/docToolchain/issues/new
    issueUrl = '##issue-url##'
    //
    // the base url for code files in github
    // Example: https://github.com/doctoolchain/doctoolchain/edit/master/src/docs
    gitRepoUrl = '##git-repo-url##'

    //
    // the location of the landing page
    landingPage = 'landingpage.gsp'
    // the menu of the microsite. A map of [code:'title'] entries to specify the order and title of the entries.
    // the codes are autogenerated from the forlder names or :jbake-menu: attribute entries from the .adoc file headers
    menu = [:]

}

//*****************************************************************************************

//Configuration for exportChangelog

exportChangelog = [:]

changelog.with {

    // Directory of which the exportChangelog task will export the changelog.
    // It should be relative to the docDir directory provided in the
    // gradle.properties file.
    dir = 'src/docs'

    // Command used to fetch the list of changes.
    // It should be a single command taking a directory as a parameter.
    // You cannot use multiple commands with pipe between.
    // This command will be executed in the directory specified by changelogDir
    // it the environment inherited from the parent process.
    // This command should produce asciidoc text directly. The exportChangelog
    // task does not do any post-processing
    // of the output of that command.
    //
    // See also https://git-scm.com/docs/pretty-formats
    cmd = 'git log --pretty=format:%x7c%x20%ad%x20%n%x7c%x20%an%x20%n%x7c%x20%s%x20%n --date=short'

}

//*****************************************************************************************
//Configureation for publishToConfluence

confluence = [:]

// 'input' is an array of files to upload to Confluence with the ability
//          to configure a different parent page for each file.
//
// Attributes
// - 'file': absolute or relative path to the asciidoc generated html file to be exported
// - 'url': absolute URL to an asciidoc generated html file to be exported
// - 'ancestorName' (optional): the name of the parent page in Confluence as string;
//                             this attribute has priority over ancestorId, but if page with given name doesn't exist,
//                             ancestorId will be used as a fallback
// - 'ancestorId' (optional): the id of the parent page in Confluence as string; leave this empty
//                            if a new parent shall be created in the space
// - 'preambleTitle' (optional): the title of the page containing the preamble (everything
//                            before the first second level heading). Default is 'arc42'
//
// The following four keys can also be used in the global section below
// - 'spaceKey' (optional): page specific variable for the key of the confluence space to write to
// - 'createSubpages' (optional): page specific variable to determine whether ".sect2" sections shall be split from the current page into subpages
// - 'pagePrefix' (optional): page specific variable, the pagePrefix will be a prefix for the page title and it's sub-pages
//                            use this if you only have access to one confluence space but need to store several
//                            pages with the same title - a different pagePrefix will make them unique
// - 'pageSuffix' (optional): same usage as prefix but appended to the title and it's subpages
// only 'file' or 'url' is allowed. If both are given, 'url' is ignored
confluence.with {
    input = [
            //[ file: "build/docs/html5/arc42.html" ],
            [ file: "build/docs/html5/service-api.html", ancestorId: "196617" ],                        // OpenAPI def in yaml
            [ file: "build/docs/html5/petstore-api.html", ancestorId: "196617" ],                       // OpenAPI def in json
            [ file: "build/docs/html5/arc42/arc42.html", ancestorId: "622599" ],
    ]

    // optional fallback if ancestorName is not set
    ancestorId  = '196617'

    // optional
    // ancestorName = 'MBB Start page'

    // endpoint of the confluenceAPI (REST) to be used
    //     api = 'https://[yourServer]/[context]/rest/api/'
    api = 'https://nikonfluence.atlassian.net/wiki/rest/api/'

//    Additionally, spaceKey, createSubpages, pagePrefix and pageSuffix can be globally defined here. The assignment in the input array has precedence

    // the key of the confluence space to write to
    spaceKey = 'WORKSPACE'

    // the title of the page containing the preamble (everything the first second level heading). Default is 'arc42'
    // preambleTitle = 'Generated by docToolchain'

    // variable to determine whether ".sect2" sections shall be split from the current page into subpages
    createSubpages = false

    // the pagePrefix will be a prefix for each page title
    // use this if you only have access to one confluence space but need to store several
    // pages with the same title - a different pagePrefix will make them unique
    pagePrefix = ''

    pageSuffix = ''

    /*
    WARNING: It is strongly recommended to store credentials securely instead of commiting plain text values to your git repository!!!

    Tool expects credentials that belong to an account which has the right permissions to to create and edit confluence pages in the given space.
    Credentials can be used in a form of:
     - passed parameters when calling script (-PconfluenceUser=myUsername -PconfluencePass=myPassword) which can be fetched as a secrets on CI/CD or
     - gradle variables set through gradle properties (uses the 'confluenceUser' and 'confluencePass' keys)
    Often, same credentials are used for Jira & Confluence, in which case it is recommended to pass CLI parameters for both entities as
    -Pusername=myUser -Ppassword=myPassword
    */

    //optional API-token to be added in case the credentials are needed for user and password exchange.
    // apikey = "[API-token]"

    // HTML Content that will be included with every page published
    // directly after the TOC. If left empty no additional content will be
    // added
    extraPageContent = '<ac:structured-macro ac:name="warning"><ac:parameter ac:name="title" /><ac:rich-text-body>This is a generated page, do not edit!</ac:rich-text-body></ac:structured-macro>'
    // extraPageContent = ''

    // enable or disable attachment uploads for local file references
    enableAttachments = false

    // default attachmentPrefix = "attachment" - All files to attach will require to be linked inside the document.
    // The expected foldername of your output dir. Default: attachment
    attachmentPrefix = "images"


    // Optional proxy configuration, only used to access Confluence
    // schema supports http and https
    // proxy = [host: 'my.proxy.com', port: 1234, schema: 'http']

    // OpenAPI Macro for Confluence
    // possible values are: "confluence-open-api", "open-api", true is the same as "confluence-open-api" for backward compatibility
    // Plugin ("confluence-open-api") Elitesoft Swagger Editor:  https://marketplace.atlassian.com/apps/1218914/open-api-swagger-editor-for-confluence?hosting=cloud&tab=overview
    // Plugin ("open-api") Open API Documentation for Confluence: https://marketplace.atlassian.com/apps/1215176/open-api-documentation-for-confluence?hosting=cloud&tab=overview
    useOpenapiMacro = "open-api"
}
