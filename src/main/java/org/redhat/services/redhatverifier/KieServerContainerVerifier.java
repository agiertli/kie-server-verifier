package org.redhat.services.redhatverifier;

import java.io.PrintStream;

import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieServerContainerVerifier {

	private static final Logger logger = LoggerFactory.getLogger(KieServerContainerVerifier.class);

	public boolean verify(String releaseId) {
		boolean verified;
		try {
			String[] gav = releaseId.split(":");
			verified = verify(gav[0], gav[1], gav[2]);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			verified = false;
		}
		return verified;
	}

	public boolean verify(String groupId, String artifactId, String version) {
		logger.info("starting verification");
		boolean verified;
		KieServices services = KieServices.Factory.get();
		try {
			ReleaseId releaseId = services.newReleaseId(groupId, artifactId, version);
			logger.info("releaseId created {} ", releaseId);
			verified = verify(releaseId);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			verified = false;
		}
		return verified;
	}

	public boolean verify(ReleaseId releaseId) {
		boolean verified;
		KieServices services = KieServices.Factory.get();
		try {
			KieContainer container = services.newKieContainer(releaseId);
			logger.info("container created {}", container);
			verified = verify(container);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			verified = false;
		}
		return verified;
	}

	public boolean verify(KieContainer container) {
		boolean verified = true;
		try {
			Results results = container.verify();
			for (Message message : results.getMessages()) {
				Level level = message.getLevel();
				switch (level) {
				case INFO:
					logger.info(message.toString());
					break;
				case WARNING:
					logger.warn(message.toString());
					break;
				case ERROR:
					logger.error(message.toString());
					verified = false;
					break;
				}
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			verified = false;
		}
		return verified;
	}

	public static void executor(String args[]) {
		boolean verified = executor2(args, System.out, System.err);
		if (!verified) {
			System.exit(1);
		}
	}

	// package-protected for JUnit testing

	static boolean executor2(String args[], PrintStream out, PrintStream err) {
		boolean verified = true;

		KieServerContainerVerifier verifier = new KieServerContainerVerifier();

		if (verifier.verify(args[0])) {
			logger.info(args[0] + " verified.");
		} else {
			logger.error(args[0] + " not verified.");
			verified = false;

			return verified;
		}

		return verified;
	}
}
