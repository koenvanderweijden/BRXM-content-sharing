package org.example.components;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsNewsComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.onehippo.cms7.essentials.components.info.EssentialsNewsComponentInfo;
import org.onehippo.cms7.essentials.components.utils.SiteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Not sure if this component stil works.
 *
 * This component combines the content from the channel folder and the common folder
 *
 */
@ParametersInfo(type = EssentialsNewsComponentInfo.class)
public class NewsListComponent extends EssentialsNewsComponent {

    private static Logger log = LoggerFactory.getLogger(NewsListComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        super.doBeforeRender(request, response);
    }

    @Override
    protected <T extends EssentialsListComponentInfo> HstQuery buildQuery(final HstRequest request, final T paramInfo, final HippoBean scope) {
        final String documentTypes = paramInfo.getDocumentTypes();
        final String[] types = SiteUtils.parseCommaSeparatedValue(documentTypes);
        if (log.isDebugEnabled()) {
            log.debug("Searching for document types:  {}, and including subtypes: {}", documentTypes, paramInfo.getIncludeSubtypes());
        }

        // Add common scope to the search
        final HstRequestContext context = RequestContextProvider.get();
        HippoBean commonScope = context.getSiteContentBaseBean().getBean("common");

        HstQueryBuilder builder = HstQueryBuilder.create(commonScope, scope);
        return paramInfo.getIncludeSubtypes() ? builder.ofTypes(types).build() : builder.ofPrimaryTypes(types).build();
    }
}
