package io.choerodon.wiki

import com.fasterxml.jackson.databind.ObjectMapper
import io.choerodon.core.oauth.CustomUserDetails
import io.choerodon.liquibase.LiquibaseConfig
import io.choerodon.liquibase.LiquibaseExecutor
import io.choerodon.wiki.app.service.WikiSpaceService
import io.choerodon.wiki.domain.application.repository.IamRepository
import io.choerodon.wiki.domain.application.repository.WikiSpaceRepository
import io.choerodon.wiki.domain.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.jwt.JwtHelper
import org.springframework.security.jwt.crypto.sign.MacSigner
import org.springframework.security.jwt.crypto.sign.Signer
import spock.mock.DetachedMockFactory

import javax.annotation.PostConstruct

@TestConfiguration
@Import(LiquibaseConfig)
class IntegrationTestConfiguration {

    private final detachedMockFactory = new DetachedMockFactory()

    @Value('${choerodon.oauth.jwt.key:choerodon}')
    String key

    @Autowired
    TestRestTemplate testRestTemplate

    @Autowired
    LiquibaseExecutor liquibaseExecutor

    final ObjectMapper objectMapper = new ObjectMapper()

    @Bean
    KafkaTemplate kafkaTemplate() {
        detachedMockFactory.Mock(KafkaTemplate)
    }

    @Bean(name = "mockiWikiSpaceWebHomeService")
    @Primary
    IWikiSpaceWebHomeService iWikiSpaceWebHomeService() {
        detachedMockFactory.Mock(IWikiSpaceWebHomeService.class);
    }

    @Bean
    @Primary
    IWikiSpaceWebPreferencesService iWikiSpaceWebPreferencesService() {
        detachedMockFactory.Mock(IWikiSpaceWebPreferencesService.class);
    }

    @Bean
    @Primary
    IWikiUserService iWikiUserService() {
        detachedMockFactory.Mock(IWikiUserService.class);
    }

    @Bean
    @Primary
    IWikiGroupService iWikiGroupService() {
        detachedMockFactory.Mock(IWikiGroupService.class);
    }

    @Bean
    @Primary
    IamRepository iamRepository() {
        detachedMockFactory.Mock(IamRepository.class);
    }

    @Bean
    @Primary
    IWikiClassService iWikiClassService() {
        detachedMockFactory.Mock(IWikiClassService.class);
    }

    @Bean
    @Primary
    WikiSpaceRepository wikiSpaceRepository() {
        detachedMockFactory.Mock(WikiSpaceRepository.class);
    }

    @Bean
    @Primary
    WikiSpaceService wikiSpaceService() {
        detachedMockFactory.Mock(WikiSpaceService.class);
    }

    @PostConstruct
    void init() {
        liquibaseExecutor.execute()
        setTestRestTemplateJWT()
    }

    private void setTestRestTemplateJWT() {
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory())
        testRestTemplate.getRestTemplate().setInterceptors([new ClientHttpRequestInterceptor() {
            @Override
            ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                httpRequest.getHeaders()
                        .add('JWT_Token', createJWT(key, objectMapper))
                return clientHttpRequestExecution.execute(httpRequest, bytes)
            }
        }])
    }

    static String createJWT(final String key, final ObjectMapper objectMapper) {
        Signer signer = new MacSigner(key)
        CustomUserDetails defaultUserDetails = new CustomUserDetails('default', 'unknown', Collections.emptyList())
        defaultUserDetails.setUserId(0L)
        defaultUserDetails.setOrganizationId(0L)
        defaultUserDetails.setLanguage('zh_CN')
        defaultUserDetails.setTimeZone('CCT')
        String jwtToken = null
        try {
            jwtToken = 'Bearer ' + JwtHelper.encode(objectMapper.writeValueAsString(defaultUserDetails), signer).getEncoded()
        } catch (IOException e) {
            e.printStackTrace()
        }
        return jwtToken
    }
}
