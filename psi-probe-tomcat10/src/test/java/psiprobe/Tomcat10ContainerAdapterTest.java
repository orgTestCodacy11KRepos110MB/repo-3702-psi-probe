/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package psiprobe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.catalina.Context;
import org.apache.catalina.Valve;
import org.apache.jasper.JspCompilationContext;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import mockit.Expectations;
import mockit.Mocked;
import psiprobe.model.ApplicationResource;

/**
 * The Class Tomcat10ContainerAdapterTest.
 */
class Tomcat10ContainerAdapterTest {

  /** The context. */
  @Mocked
  Context context;

  /**
   * Creates the valve.
   */
  @Test
  public void createValve() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    Valve valve = adapter.createValve();
    assertEquals("Tomcat10AgentValve[Container is null]", valve.toString());
  }

  /**
   * Can bound to null.
   */
  @Test
  public void canBoundToNull() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertFalse(adapter.canBoundTo(null));
  }

  /**
   * Can bound to tomcat9.
   */
  @Test
  public void canBoundToTomcat9() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertTrue(adapter.canBoundTo("Apache Tomcat/9.0"));
  }

  /**
   * Can bound to tomEE9.
   */
  @Test
  public void canBoundToTomEE9() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertTrue(adapter.canBoundTo("Apache Tomcat (TomEE)/9.0"));
  }

  /**
   * Can bound to pivotal9.
   */
  @Test
  public void canBoundToPivotal9() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertTrue(adapter.canBoundTo("Pivotal tc..../9.0"));
  }

  /**
   * Can bound to other.
   */
  @Test
  public void canBoundToOther() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertFalse(adapter.canBoundTo("Other"));
  }

  /**
   * Filter mappings.
   */
  @Test
  public void filterMappings() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    FilterMap map = new FilterMap();
    map.addServletName("psi-probe");
    map.addURLPattern("/psi-probe");
    assertEquals(2, adapter.getFilterMappings(map, "dispatcherMap", "filterClass").size());
  }

  /**
   * Creates the jsp compilation context.
   */
  @Test
  public void createJspCompilationContext() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    JspCompilationContext context = adapter.createJspCompilationContext("name", null, null, null,
        ClassLoader.getSystemClassLoader());
    assertEquals("org.apache.jsp.name", context.getFQCN());
  }

  /**
   * Adds the context resource link.
   */
  @Test
  public void addContextResourceLink() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    adapter.addContextResourceLink(context, new ArrayList<ApplicationResource>(), false);
  }

  /**
   * Adds the context resource.
   */
  @Test
  public void addContextResource() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    adapter.addContextResource(context, new ArrayList<ApplicationResource>(), false);
  }

  /**
   * Gets the application filter maps.
   */
  @Test
  public void applicationFilterMaps() {
    Assertions.assertNotNull(new Expectations() {
      {
        context.findFilterMaps();
        result = new FilterMap();
      }
    });

    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertEquals(0, adapter.getApplicationFilterMaps(context).size());
  }

  /**
   * Application filters.
   */
  @Test
  public void applicationFilters() {
    Assertions.assertNotNull(new Expectations() {
      {
        context.findFilterDefs();
        result = new FilterDef();
      }
    });

    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertEquals(1, adapter.getApplicationFilters(context).size());
  }

  /**
   * Application init params.
   */
  @Test
  public void applicationInitParams() {
    Assertions.assertNotNull(new Expectations() {
      {
        context.findApplicationParameters();
        result = new ApplicationParameter();
      }
    });
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertEquals(0, adapter.getApplicationInitParams(context).size());
  }

  /**
   * Resource exists.
   */
  @Test
  public void resourceExists() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertTrue(adapter.resourceExists("name", context));
  }


  /**
   * Resource stream.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void resourceStream() throws IOException {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    adapter.getResourceStream("name", context);
  }

  /**
   * Resource attributes.
   */
  @Test
  public void resourceAttributes() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    adapter.getResourceAttributes("name", context);
  }

  /**
   * Gets the naming token.
   */
  @Test
  public void getNamingToken() {
    final Tomcat10ContainerAdapter adapter = new Tomcat10ContainerAdapter();
    assertNull(adapter.getNamingToken(context));
  }

}
