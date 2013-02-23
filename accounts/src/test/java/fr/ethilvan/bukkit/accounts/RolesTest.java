package fr.ethilvan.bukkit.accounts;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.bukkit.ChatColor;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.ethilvan.bukkit.api.accounts.Role;

public class RolesTest {

    private static AccountsConfig config;

    static {
        InputStream stream =
                AccountsPlugin.class.getResourceAsStream("/config-test.json");
        Gson gson = new GsonBuilder().create();
        config = gson.fromJson(new InputStreamReader(stream),
                AccountsConfig.class);
    }

    private Role admin;
    private Role modo;
    private Role basic;

    @Before
    public void setup() {
        Map<String, Role> roles = new RolesConfigReader(config.getRoles())
                .read();
        admin = roles.get("admin");
        modo = roles.get("modo");
        basic = roles.get("basic");
    }

    @Test
    public void testNames() {
        assertEquals("Admin", admin.getName());
        assertEquals("Modo",  modo.getName());
        assertEquals("Basic", basic.getName());
    }

    @Test
    public void testColors() {
        assertEquals(ChatColor.DARK_RED, admin.getColor());
        assertEquals(ChatColor.BLUE,     modo.getColor());
        assertEquals(ChatColor.WHITE,    basic.getColor());
    }

    @Test
    public void testInheritance() {
        assertTrue( admin.inherit(admin));
        assertTrue( admin.inherit(modo));
        assertTrue( admin.inherit(basic));

        assertFalse(admin.strictInherit(admin));
        assertTrue( admin.strictInherit(modo));
        assertTrue( admin.strictInherit(basic));

        assertFalse(modo.inherit(admin));
        assertTrue( modo.inherit(modo));
        assertTrue( modo.inherit(basic));

        assertFalse(modo.strictInherit(admin));
        assertFalse(modo.strictInherit(modo));
        assertTrue( modo.strictInherit(basic));

        assertFalse(basic.inherit(admin));
        assertFalse(basic.inherit(modo));
        assertTrue( basic.inherit(basic));

        assertFalse(basic.strictInherit(admin));
        assertFalse(basic.strictInherit(modo));
        assertFalse(basic.strictInherit(basic));
    }
}
