/*
 * Copyright (c) 2011-2013 The original author or authors
 *  ------------------------------------------------------
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *      The Eclipse Public License is available at
 *      http://www.eclipse.org/legal/epl-v10.html
 *
 *      The Apache License v2.0 is available at
 *      http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.core.http;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;

/**
 * Represents an HTTP connection.
 * <p/>
 * HTTP/1.x connection provides an limited implementation, the following methods are implemented:
 * <ul>
 *   <li>{@link #close}</li>
 *   <li>{@link #closeHandler}</li>
 *   <li>{@link #exceptionHandler}</li>
 * </ul>
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface HttpConnection {

  /**
   * @return the current connection window size or {@code -1} for HTTP/1.x
   */
  default int getWindowSize() {
    return -1;
  }

  /**
   * Update the current connection wide window size to a new size.
   * <p/>
   * Increasing this value, gives better performance when several data streams are multiplexed
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param windowSize the new window size
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  default HttpConnection setWindowSize(int windowSize) {
    return this;
  }

  /**
   * Like {@link #goAway(long, int)} with a last stream id {@code 2^31-1}.
   */
  @Fluent
  default HttpConnection goAway(long errorCode) {
    return goAway(errorCode, Integer.MAX_VALUE);
  }

  /**
   * Like {@link #goAway(long, int, Buffer)} with no buffer.
   */
  @Fluent
  default HttpConnection goAway(long errorCode, int lastStreamId) {
    return goAway(errorCode, lastStreamId, null);
  }

  /**
   * Send a go away frame to the remote endpoint of the connection.
   * <p/>
   * <ul>
   *   <li>a {@literal GOAWAY} frame is sent to the to the remote endpoint with the {@code errorCode} and {@code debugData}</li>
   *   <li>any stream created after the stream identified by {@code lastStreamId} will be closed</li>
   *   <li>for an {@literal errorCode} is different than {@code 0} when all the remaining streams are closed this connection will be closed automatically</li>
   * </ul>
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param errorCode the {@literal GOAWAY} error code
   * @param lastStreamId the last stream id
   * @param debugData additional debug data sent to the remote endpoint
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection goAway(long errorCode, int lastStreamId, Buffer debugData);

  /**
   * Set an handler called when a {@literal GOAWAY} frame is received.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param handler the handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection goAwayHandler(@Nullable Handler<GoAway> handler);

  /**
   * Set an handler called when a {@literal GOAWAY} frame has been sent or received and all connections are closed.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param handler the handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection shutdownHandler(@Nullable  Handler<Void> handler);

  /**
   * Initiate a connection shutdown, a go away frame is sent and the connection is closed when all current active streams
   * are closed or after a time out of 30 seconds.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection shutdown();

  /**
   * Initiate a connection shutdown, a go away frame is sent and the connection is closed when all current streams
   * will be closed or the {@code timeout} is fired.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param timeoutMs the timeout in milliseconds
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection shutdown(long timeoutMs);

  /**
   * Set a close handler. The handler will get notified when the connection is closed.
   *
   * @param handler the handler to be notified
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection closeHandler(Handler<Void> handler);

  /**
   * Close the connection and all the currently active streams.
   * <p/>
   * An HTTP/2 connection will send a {@literal GOAWAY} frame before.
   */
  void close();

  /**
   * @return the latest server settings acknowledged by the remote endpoint - this is not implemented for HTTP/1.x
   */
  Http2Settings settings();

  /**
   * Send to the remote endpoint an update of the server settings.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param settings the new settings
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection updateSettings(Http2Settings settings);

  /**
   * Send to the remote endpoint an update of this endpoint settings
   * <p/>
   * The {@code completionHandler} will be notified when the remote endpoint has acknowledged the settings.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param settings the new settings
   * @param completionHandler the handler notified when the settings have been acknowledged by the remote endpoint
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection updateSettings(Http2Settings settings, Handler<AsyncResult<Void>> completionHandler);

  /**
   * @return the current remote endpoint settings for this connection - this is not implemented for HTTP/1.x
   */
  Http2Settings remoteSettings();

  /**
   * Set an handler that is called when remote endpoint {@link Http2Settings} are updated.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param handler the handler for remote endpoint settings
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection remoteSettingsHandler(Handler<Http2Settings> handler);

  /**
   * Send a {@literal PING} frame to the remote endpoint.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param data the 8 bytes data of the frame
   * @param pongHandler an async result handler notified with pong reply or the failure
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection ping(Buffer data, Handler<AsyncResult<Buffer>> pongHandler);

  /**
   * Set an handler notified when a {@literal PING} frame is received from the remote endpoint.
   * <p/>
   * This is not implemented for HTTP/1.x.
   *
   * @param handler the handler to be called when a {@literal PING} is received
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection pingHandler(@Nullable Handler<Buffer> handler);

  /**
   * Set an handler called when a connection error happens
   *
   * @param handler the handler
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  HttpConnection exceptionHandler(Handler<Throwable> handler);

}
