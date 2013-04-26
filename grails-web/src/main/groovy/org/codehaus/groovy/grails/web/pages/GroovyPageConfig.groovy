package org.codehaus.groovy.grails.web.pages

import groovy.transform.CompileStatic

import org.codehaus.groovy.grails.plugins.GrailsPluginInfo
import org.codehaus.groovy.grails.web.util.WithCodecHelper

@CompileStatic
class GroovyPageConfig {
    /**  outCodec escapes the static html parts coming from the GSP file to output */
    public static String OUT_CODEC_NAME="out"
    /** expressionCodec escapes values inside ${} to output */
    public static String EXPRESSION_CODEC_NAME="expression"
    /**  staticCodec escapes the static html parts coming from the GSP file to output */
    public static String STATIC_CODEC_NAME="static"
    public static String TAGLIB_CODEC_NAME="taglib"
    public static final String TAGLIB_DEFAULT_CODEC_NAME="taglibDefault"
    
    public static final String INHERIT_SETTING_NAME="inherit"
    
    public static final Set<String> VALID_CODEC_SETTING_NAMES = ([OUT_CODEC_NAME, EXPRESSION_CODEC_NAME, STATIC_CODEC_NAME, TAGLIB_CODEC_NAME, TAGLIB_DEFAULT_CODEC_NAME] as Set).asImmutable()

    private static final Map<String, String> defaultSettings =
                                                        [(EXPRESSION_CODEC_NAME): 'none',
                                                            (STATIC_CODEC_NAME): 'none',
                                                            (OUT_CODEC_NAME): 'none',
                                                            (TAGLIB_CODEC_NAME): 'none',
                                                            (TAGLIB_DEFAULT_CODEC_NAME): 'none']

    Map flatConfig

    GroovyPageConfig(Map flatConfig) {
        this.flatConfig = flatConfig
    }

    public String getCodecSettings(GrailsPluginInfo pluginInfo, String codecWriterName) {
        if(!codecWriterName) return null

        String gspCodecsPrefix = "${pluginInfo ? pluginInfo.name + '.' : ''}${GroovyPageParser.CONFIG_PROPERTY_GSP_CODECS}"
        Map codecSettings = (Map)flatConfig.get(gspCodecsPrefix)
        String codecInfo = null
        if(!codecSettings) {
            if(codecWriterName==EXPRESSION_CODEC_NAME) {
                codecInfo = flatConfig.get(GroovyPageParser.CONFIG_PROPERTY_DEFAULT_CODEC)?.toString()
            }
        } else {
            codecInfo = codecSettings.get(codecWriterName)?.toString()
        }
        if(!codecInfo) {
            codecInfo = defaultSettings.get(codecWriterName)
        }

        codecInfo
    }
}
