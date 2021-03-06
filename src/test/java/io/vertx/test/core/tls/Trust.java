package io.vertx.test.core.tls;

import io.vertx.core.net.JksOptions;
import io.vertx.core.net.PemTrustOptions;
import io.vertx.core.net.PfxOptions;
import io.vertx.core.net.TrustOptions;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Trust<T extends TrustOptions> extends Supplier<T> {

  Trust<TrustOptions> NONE = () -> null;
  Trust<JksOptions> SERVER_JKS = () -> new JksOptions().setPath("tls/client-truststore.jks").setPassword("wibble");
  Trust<JksOptions> CLIENT_JKS = () -> new JksOptions().setPath("tls/server-truststore.jks").setPassword("wibble");
  Trust<PfxOptions> SERVER_PKCS12 = () -> new PfxOptions().setPath("tls/client-truststore.p12").setPassword("wibble");
  Trust<PfxOptions> CLIENT_PKCS12 = () -> new PfxOptions().setPath("tls/server-truststore.p12").setPassword("wibble");
  Trust<PemTrustOptions> SERVER_PEM = () -> new PemTrustOptions().addCertPath("tls/server-cert.pem");
  Trust<PemTrustOptions> CLIENT_PEM = () -> new PemTrustOptions().addCertPath("tls/client-cert.pem");
  Trust<JksOptions> SERVER_JKS_ROOT_CA = () -> new JksOptions().setPath("tls/client-truststore-root-ca.jks").setPassword("wibble");
  Trust<PfxOptions> SERVER_PKCS12_ROOT_CA = () -> new PfxOptions().setPath("tls/client-truststore-root-ca.p12").setPassword("wibble");
  Trust<PemTrustOptions> SERVER_PEM_ROOT_CA = () -> new PemTrustOptions().addCertPath("tls/root-ca/ca-cert.pem");
  Trust<PemTrustOptions> CLIENT_PEM_ROOT_CA = () -> new PemTrustOptions().addCertPath("tls/root-ca/ca-cert.pem");
  Trust<PemTrustOptions> SERVER_PEM_ROOT_CA_AND_OTHER_CA = () -> new PemTrustOptions().addCertPath("tls/root-ca/ca-cert.pem").addCertPath("tls/other-ca/ca-cert.pem");

}
