//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt

package all.shared.gradle.testfixtures;

import org.gradle.api.Project;
import org.gradle.api.internal.plugins.PluginManagerInternal;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.testfixtures.ProjectBuilder;

import org.mockito.Mockito;

public final class SpyProjectFactory {
  public static final String PROJECT_NAME = "Spy Project";

  /**
   * Default ProjectBuilder instance.
   */
  public static final ProjectBuilder builder = ProjectBuilder.builder();

  private SpyProjectFactory() { }

  /**
   * Builds a Gradle Project with provided builder.
   * @param builder ProjectBuilder to use.
   * @return A Project:
   * <ul>
   *   <li>pluginManager field will be mocked.
   *   <li>plugins field will be mocked.
   *   <li>logger field will be mocked.
   * </ul>
   */
  public static Project build(final ProjectBuilder builder) {
    final Project newSpyProject = Mockito.spy(builder.build());

    Mockito.doReturn(Mockito.mock(PluginManagerInternal.class))
      .when(newSpyProject)
      .getPluginManager();
    Mockito.doReturn(Mockito.mock(PluginContainer.class))
      .when(newSpyProject)
      .getPlugins();
    Mockito.doReturn(Mockito.mock(Logger.class))
      .when(newSpyProject)
      .getLogger();

    return newSpyProject;
  }

  /**
   * Builds a Gradle Project with Default builder.
   * @return A Project:
   * <ul>
   *   <li>Project name will be PROJECT_NAME.
   *   <li>pluginManager field will be mocked.
   *   <li>plugins field will be mocked.
   *   <li>logger field will be mocked.
   * </ul>
   */
  public static Project build() {
    return build(builder.withName(PROJECT_NAME));
  }
}
