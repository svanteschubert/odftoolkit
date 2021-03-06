package com.mobanisto.odftoolkit.website;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import de.topobyte.system.utils.SystemPaths;

public class TestGenerateWebsitePartial
{

	public static void main(String[] args) throws IOException
	{
		Path repo = SystemPaths.HOME.resolve("github/sebkur/odftoolkit");

		WebsiteGenerator websiteGenerator = new WebsiteGenerator(repo, repo,
				SystemPaths.HOME.resolve("github/sebkur/odftoolkit-gh-pages"));
		websiteGenerator
				.generate(Arrays.asList("index.mdtext", "odfdom/index.mdtext"));
	}

}
