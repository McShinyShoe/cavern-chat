package net.shinyshoe.cavernChat.client.channel;

/**
 * A chat channel you can be talking in, and the colour the server paints it.
 * See {@link ChannelRegistry} for the ones The Cavern actually has.
 */
public record ChatChannel(int color, String name) {
}
