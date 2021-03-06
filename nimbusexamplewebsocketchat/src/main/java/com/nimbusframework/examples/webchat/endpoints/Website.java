package com.nimbusframework.examples.webchat.endpoints;


import com.nimbusframework.nimbuscore.annotations.deployment.FileUpload;
import com.nimbusframework.nimbuscore.annotations.file.FileStorageBucketDefinition;

import static com.nimbusframework.examples.webchat.Configuration.DEV_STAGE;
import static com.nimbusframework.examples.webchat.Configuration.PRODUCTION_STAGE;

@FileStorageBucketDefinition(
        bucketName = Website.WEBSITE_BUCKET,
        staticWebsite = true,
        indexFile = "webchat.html"
)
@FileUpload(fileStorageBucket = Website.class,
            localPath = "src/website",
            targetPath = "",
            substituteNimbusVariables = true
)
public class Website {
    public static final String WEBSITE_BUCKET = "WebChatNimbus";
}

