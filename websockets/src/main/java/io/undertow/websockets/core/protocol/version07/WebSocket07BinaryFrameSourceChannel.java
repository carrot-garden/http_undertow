/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.undertow.websockets.core.protocol.version07;

import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSocketFrameType;
import io.undertow.websockets.core.FixedPayloadFrameSourceChannel;
import org.xnio.channels.StreamSourceChannel;


/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
class WebSocket07BinaryFrameSourceChannel extends FixedPayloadFrameSourceChannel {
    WebSocket07BinaryFrameSourceChannel(WebSocketChannel.StreamSourceChannelControl streamSourceChannelControl, StreamSourceChannel channel, WebSocketChannel wsChannel, long payloadSize, int rsv, boolean finalFragment, Masker masker) {
        super(streamSourceChannelControl, channel, wsChannel, WebSocketFrameType.BINARY, payloadSize, rsv, finalFragment, masker);
    }

    WebSocket07BinaryFrameSourceChannel(WebSocketChannel.StreamSourceChannelControl streamSourceChannelControl, StreamSourceChannel channel, WebSocketChannel wsChannel, long payloadSize, int rsv, boolean finalFragment) {
        super(streamSourceChannelControl, channel, wsChannel, WebSocketFrameType.BINARY, payloadSize, rsv, finalFragment);
    }
}
