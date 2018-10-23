//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt

package all.shared.gradle.testfixtures;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mockingDetails;

public class SpyProjectFactoryTest {
  @Test
  public void shouldProvideDefaultBuilder() {
    assertNotNull(SpyProjectFactory.builder);
  }

  @Test
  public void shouldBuildProjectWithCustomBuilder() {
    final ProjectBuilder builder = ProjectBuilder.builder()
      .withName("someBuilder");
    final Project spyProject = SpyProjectFactory.build(builder);

    assertTrue(mockingDetails(spyProject)
      .isSpy());
    assertEquals("someBuilder", spyProject.getName());
    assertTrue(mockingDetails(spyProject.getPluginManager())
      .isMock());
    assertTrue(mockingDetails(spyProject.getPlugins())
      .isMock());
    assertTrue(mockingDetails(spyProject.getLogger())
      .isMock());
  }

  @Test
  public void shouldBuildProject() {
    final Project spyProject = SpyProjectFactory.build();

    assertTrue(mockingDetails(spyProject)
      .isSpy());
    assertEquals(SpyProjectFactory.PROJECT_NAME, spyProject.getName());
  }
}

