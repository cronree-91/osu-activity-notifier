package jp.cron.sample.util;

import net.dv8tion.jda.api.Permission;

public class PermissionUtil {
    public static String localizedName(Permission p) {
        String permJp = p.getName();
        switch (p) {
            case MANAGE_CHANNEL:
                permJp = "チャンネルの管理";
                break;
            case MESSAGE_SEND:
                permJp = "メッセージの読み取り";
                break;
            case VIEW_CHANNEL:
                permJp = "チャンネルの閲覧";
                break;
            case MESSAGE_EMBED_LINKS:
                permJp = "埋め込みリンク";
                break;
            case VOICE_MOVE_OTHERS:
                permJp = "メンバーを移動";
                break;
            case MANAGE_PERMISSIONS:
                permJp = "ロールの管理";
                break;
            case MANAGE_ROLES:
                permJp = "ロールの管理";
                break;
        }
        return permJp;
    }
}
