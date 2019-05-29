package ch.bfh.bti7081.s2019.blue.server.i18n;

import ch.bfh.bti7081.s2019.blue.client.i18n.TranslationProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@Component
public class ServerConstantsProvider implements InvocationHandler {

    private static final String BUNDLE_PREFIX = "serverConstants";

    private static final LoadingCache<Locale, ResourceBundle> bundleCache = CacheBuilder
            .newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<Locale, ResourceBundle>() {

                @Override
                public ResourceBundle load(final Locale key) throws Exception {
                    return initializeBundle(key);
                }
            });

    private static final ServerConstants constants = (ServerConstants) Proxy.newProxyInstance(ServerConstantsProvider.class.getClassLoader(), new Class<?>[]{ServerConstants.class}, new ServerConstantsProvider());

    public ServerConstants get() {
        return constants;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("hashCode".equals(method.getName())) {
            return 0;
        }

        final ResourceBundle bundle = bundleCache.getUnchecked(LocaleContextHolder.getLocale());

        String key = method.getName();
        String value;
        try {
            value = bundle.getString(key);
        } catch (MissingResourceException mrx) {
            value = "***" + key + "***";
        }

        if (args != null && args.length > 0) {
            value = MessageFormat.format(value, args);
        }
        return value;
    }

    private static ResourceBundle initializeBundle(final Locale locale) {
        return readProperties(locale);
    }

    protected static ResourceBundle readProperties(final Locale locale) {
        final ClassLoader cl = TranslationProvider.class.getClassLoader();
        return ResourceBundle.getBundle("i18n/" + BUNDLE_PREFIX, locale, cl);
    }
}
