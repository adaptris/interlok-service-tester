package com.adaptris.tester.runtime.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import com.adaptris.tester.runtime.ServiceTestConfig;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.adaptris.tester.runtime.services.sources.InlineSource;

/**
 * @author mwarman
 */
public class EmbeddedTestClientTest extends ClientCase{



  @Test
  public void testSharedComponentProvider() throws Exception{
    TestClient embeddedTestClient = createTestClient();
    embeddedTestClient.init(new ServiceTestConfig());
    TestMessage result = embeddedTestClient.applyService("<shared-service>\n" +
        "                        <lookup-name>Add2</lookup-name>\n" +
        "                      </shared-service>", new TestMessage());
    assertTrue(result.getMessageHeaders().containsKey("key2"));
    assertEquals("val2", result.getMessageHeaders().get("key2"));
  }

  @Override
  protected TestClient createTestClient() {
    EmbeddedTestClient embeddedTestClient = new EmbeddedTestClient();
    SharedComponentProvider sharedComponentProvider = new SharedComponentProvider();
    ServiceProvider service = new ServiceProvider();
    InlineSource source = new InlineSource();
    source.setXml("<add-metadata-service>\n" +
        "            <unique-id>Add2</unique-id>\n" +
        "            <metadata-element>\n" +
        "                <key>key2</key>\n" +
        "                <value>val2</value>\n" +
        "            </metadata-element>\n" +
        "        </add-metadata-service>");
    service.setSource(source);
    sharedComponentProvider.setServices(Collections.singletonList(service));
    embeddedTestClient.setSharedComponentsProvider(sharedComponentProvider);
    return embeddedTestClient;
  }

}