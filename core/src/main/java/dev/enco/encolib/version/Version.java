package dev.enco.encolib.utils.version;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public enum Version {
    V1_16("1.16"),
    V1_17("1.17"),
    V1_18("1.18"),
    V1_19("1.19"),
    V1_20("1.20"),
    V1_21("1.21"),
    V26("26"),
    UNKNOWN("unknown");

    private static final Version CURRENT;

    static {
        String versionString = Bukkit.getBukkitVersion();
        Version detected = UNKNOWN;

        for (Version v : values()) {
            if (v == UNKNOWN) continue;

            if (versionString.contains(v.ver)) {
                detected = v;
                break;
            }
        }

        CURRENT = detected;
    }

    public static Version getServerVersion() {
        return CURRENT;
    }

    private final String ver;

    public boolean isHigherThan(Version version) {
        return this.ordinal() > version.ordinal();
    }

    public boolean isHigherThanOrEqualTo(Version version) {
        return this.ordinal() >= version.ordinal();
    }

    public boolean isLowerThan(Version version) {
        return this.ordinal() < version.ordinal();
    }

    public boolean isLowerThanOrEqualTo(Version version) {
        return this.ordinal() <= version.ordinal();
    }

    public boolean isEqual(Version version) {
        return this == version;
    }
}
