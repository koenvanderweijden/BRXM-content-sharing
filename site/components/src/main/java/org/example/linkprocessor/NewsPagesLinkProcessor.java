package org.example.linkprocessor;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.linking.HstLinkProcessorTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class NewsPagesLinkProcessor extends HstLinkProcessorTemplate {

    private static Logger log = LoggerFactory.getLogger(NewsPagesLinkProcessor.class);


    // used to get document correct from repository
    @Override
    protected HstLink doPreProcess(HstLink link) {
        log.info("preProcess, path: {}", link.getPath());


        if(link.getPath().startsWith("news/")) {
            try {
                HstRequestContext hstRequestContext = RequestContextProvider.get();
                Session session = hstRequestContext.getSession();
                String basePath = "/content/documents/content";
                String mountPath = link.getMount().getAlias();
                String linkPath = link.getPath().replaceFirst("news/common/", "");
                String newPath = basePath + "/" + mountPath + "/" + linkPath ;

                // Check if their is a overridden version of the document
                if (session.nodeExists(newPath.replace(".html", ""))) {
                    link.setPath("news/" + mountPath + "/" + linkPath);
                }
            } catch (RepositoryException e) {
                log.error("Something went wrong: {}", e.getMessage());
            }
        }

        return link;
    }


    @Override
    protected HstLink doPostProcess(HstLink link) {
        log.info("post, path: {}", link.getPath());
        return link;
    }

}
