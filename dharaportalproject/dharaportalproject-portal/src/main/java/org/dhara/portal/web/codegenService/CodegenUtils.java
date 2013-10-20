package org.dhara.portal.web.codegenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 7/23/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Util class to keep constants and values and helper methods
 */
public class CodegenUtils {
    private static List<String> methods=new ArrayList<String>();
    private static List<String> imports=new ArrayList<String>();
    private static Map<String,String> literals=new HashMap<String,String>();
    private static List<String>  complexTypes=new ArrayList<String>();
    private static List<String>  algorithms=new ArrayList<String>();
    public static final String getOutputDataTypeMethod="public Class getOutputDataType(String identifier)";
    public static final String getInputDataTypeMethod="public Class getInputDataType(String identifier)";
    public static final String getOutputIdentifiersMethod="public List<String> getOutputIdentifiers()";
    public static final String getInputIdentifiersMethod="public List<String> getInputIdentifiers()";
    public static final String runMethod="public Map<String, IData> run(Map<String, List<IData>> inputMap)";
    public static final String literalImports="import org.n52.wps.io.data.binding.literal.*;";
    public static final String complexImports="import org.n52.wps.io.data.binding.complex.*;";
    public static final String algorithmImports="import org.n52.wps.server.*;";
    public static final String dataImports="import org.n52.wps.io.data.*;";
    public static final String utilImports="import java.util.*;";
    public static final String loggerImports="import org.apache.log4j.Logger;";
    public static final String ioImports="import java.io.*;";
    public static final String netImports="import java.net.*;";
    public static final String jsonImports="import org.json.simple.parser.*;";
    public static final String httpImports="import javax.net.ssl.HttpsURLConnection;";
    public static final String jsonParserImports="import org.json.simple.parser.JSONParser;";
    public static final String jsonObjectImports="import org.json.simple.JSONObject;";
    public static final String jsonExceptionImports="import org.json.simple.parser.ParseException;";
    public static final String defaultPackage ="package org.dhara.wps;";
    public static final String defaultExtendingClass="AbstractSelfDescribingAlgorithm";
    public static final String COMPLEX_ASCII_GRASS_DATA_BINDING="AsciiGrassDataBinding";
    public static final String COMPLEX_GENERIC_FILE_DATA_BINDING="GenericFileDataBinding";
    public static final String COMPLEX_GENERIC_GEOTIFF_DATA_BINDING="GeotiffDataBinding";
    public static final String COMPLEX_GT_RASTER_DATA_BINDING="GTRasterDataBinding";
    public static final String COMPLEX_GT_VECTOR_DATA_BINDING="GTVectorDataBinding";
    public static final String COMPLEX_PLAIN_STRING_DATA_BINDING="PlainStringDataBinding";
    public static final String COMPLEX_SHAPE_FILE_DATA_BINDING="ShapefileDataBinding";
    public static final String LITERAL_BOOLEAN_BINDING="LiteralBooleanBinding";
    public static final String LITERAL_STRING_BINDING="LiteralStringBinding";
    public static final String LITERAL_INT_BINDING="LiteralIntBinding";
    public static final String LITERAL_DOUBLE_BINDING="LiteralDoubleBinding";
    public static final String LITERAL_SHORT_BINDING="LiteralShortBinding";
    public static final String LITERAL_FLOAT_BINDING="LiteralFloatBinding";
    public static final String LITERAL_LONG_BINDING="LiteralLongBinding";
    public static final String LITERAL_DATA_TIME_BINDING="LiteralDataTimeBinding";
    public static final String CONVEX_HULL_ALGORITHM="ConvexHullAlgorithm";
    public static final String COORDINATE_TRANSFORM_ALGORITHM="CoordinateTransformAlgorithm";
    public static final String DIFFERENCE_ALGORITHM="DifferenceAlgorithm";
    public static final String RASTER_ALGORITHM="IntersectionAlgorithm";
    public static final String DOUGLAS_PEUCKER_ALGORITHM="DouglasPeuckerAlgorithm";
    public static final String TOPOLOGY_PRESERVE_SIMPLIFICATION_ALGORITHM="TopologyPreservingSimplificationAlgorithm";
    public static final String CONTAINS_ALGORITHM="ContainsAlgorithm";
    public static final String CROSSES_ALGORITHM="CrossesAlgorithm";
    public static final String DISJOINT_ALGORITHM="DisjointAlgorithm";
    public static final String DISTANCE_ALGORITHM="DistanceAlgorithm";
    public static final String EQUALS_ALGORITHM="EqualsAlgorithm";
    public static final String INTERSECTS_ALGORITHM="IntersectsAlgorithm";
    public static final String OVERLAPS_ALGORITHM="OverlapsAlgorithm";
    public static final String TOUCHES_ALGORITHM="TouchesAlgorithm";
    public static final String WITHIN_ALGORITHM="WithinAlgorithm";
    public static final String EXECUTION_SERVLET="http://localhost:8090/portal/connect/ExecutionServlet";

    static {
        getMethods().add(getInputDataTypeMethod);
        getMethods().add(getOutputDataTypeMethod);
        getMethods().add(getOutputIdentifiersMethod);
        getMethods().add(getInputIdentifiersMethod);
        getMethods().add(runMethod);

        getImports().add(literalImports);
        getImports().add(complexImports);
        getImports().add(algorithmImports);
        getImports().add(dataImports);
        getImports().add(utilImports);
        getImports().add(loggerImports);
        getImports().add(ioImports);
        getImports().add(netImports);
        getImports().add(httpImports);
        getImports().add(jsonImports);
        getImports().add(jsonObjectImports);
        getImports().add(jsonParserImports);
        getImports().add(jsonExceptionImports);

        getLiterals().put("integer", "LiteralIntBinding");
        getLiterals().put("String", "LiteralStringBinding");
        getLiterals().put("Boolean", "LiteralBooleanBinding");
        getLiterals().put("Short", "LiteralShortBinding");
        getLiterals().put("Double", "LiteralDoubleBinding");
        getLiterals().put("Float", "LiteralFloatBinding");
        getLiterals().put("Date", "LiteralDataTimeBinding");

        getComplexTypes().add("AsciiGrassDataBinding");
        getComplexTypes().add("GenericFileDataBinding");
        getComplexTypes().add("GeotiffDataBinding");
        getComplexTypes().add("GTRasterDataBinding");
        getComplexTypes().add("GTVectorDataBinding");
        getComplexTypes().add("PlainStringDataBinding");
        getComplexTypes().add("ShapefileDataBinding");

        getAlgorithms().add("ConvexHullAlgorithm");
        getAlgorithms().add("CoordinateTransformAlgorithm");
        getAlgorithms().add("DifferenceAlgorithm");
        getAlgorithms().add("IntersectionAlgorithm");
        getAlgorithms().add("DouglasPeuckerAlgorithm");
        getAlgorithms().add("TopologyPreservingSimplificationAlgorithm");
        getAlgorithms().add("ContainsAlgorithm");
        getAlgorithms().add("CrossesAlgorithm");
        getAlgorithms().add("DisjointAlgorithm");
        getAlgorithms().add("DistanceAlgorithm");
        getAlgorithms().add("EqualsAlgorithm");
        getAlgorithms().add("OverlapsAlgorithm");
        getAlgorithms().add("TouchesAlgorithm");
        getAlgorithms().add("WithinAlgorithm");

    }

    public static List<String> getMethods() {
        return methods;
    }

    public static List<String> getImports() {
        return imports;
    }

    public static Map<String, String> getLiterals() {
        return literals;
    }

    public static List<String> getComplexTypes() {
        return complexTypes;
    }

    public static List<String> getAlgorithms() {
        return algorithms;
    }
}
