${package}

<#list imports as import>
${import}
</#list>

public class ${processName} extends ${extendingClass} {

private static Logger LOGGER = Logger.getLogger(${processName}.class);

<#list inputIdentifiers as inputIdentifier>
private final String inputIdentifier_${inputIdentifier} = "${inputIdentifier}";
</#list>

<#list outputIdentifiers as outputIdentifier>
private final String outputIdentifier_${outputIdentifier} = "${outputIdentifier}";
</#list>
private final String USER_AGENT = "Mozilla/5.0";
private String url = "${executionServlet}";

@Override
public List<String> getInputIdentifiers() {
    List<String> identifierList = new ArrayList<String>();
    <#list inputIdentifiers as inputIdentifier>
        identifierList.add("${inputIdentifier}");
    </#list>
        return identifierList;
        }

        @Override
        public List<String> getOutputIdentifiers() {
            List<String> identifierList = new ArrayList<String>();
            <#list outputIdentifiers as outputIdentifier>
                identifierList.add("${outputIdentifier}");
            </#list>
                return identifierList;
                }

                @Override
                public Class<?> getInputDataType(String identifier) {
            <#list inputIdentifiers as inputIdentifier>
                if (identifier.equalsIgnoreCase(inputIdentifier_${inputIdentifier})) {
                <#list inputMappings as inputMapping>
                    <#if inputMapping.identifier = inputIdentifier>
                        return ${inputMapping.mappingClass}.class;
                    </#if>
                </#list>
                }
            </#list>
                return null;
                }

                @Override
                public Class<?> getOutputDataType(String identifier) {
            <#list outputIdentifiers as outputIdentifier>
                if (identifier.equalsIgnoreCase(outputIdentifier_${outputIdentifier})) {
                <#list outputMappings as outputMapping>
                    <#if outputMapping.identifier = outputIdentifier>
                        return ${outputMapping.mappingClass}.class;
                    </#if>
                </#list>
                }
            </#list>
                return null;
                }

                @SuppressWarnings({ "unchecked" })
                @Override
                public Map<String, IData> run(Map<String, List<IData>> inputDataList) {

                <#list inputIdentifiers as inputIdentifier>
                    List<IData> ${inputIdentifier}List = inputDataList.get("${inputIdentifier}");
                </#list>

                <#list inputIdentifiers as inputIdentifier>
                    if (${inputIdentifier}List.size() == 0) {
                    throw new RuntimeException("Invalid Input Parameters");
                    }
                </#list>

                    //Other elements are attributes
                <#list inputIdentifiers as inputIdentifier>
                    IData ${inputIdentifier}Wrapped = ${inputIdentifier}List.get(0);
                </#list>

                <#list inputIdentifiers as inputIdentifier>
                    String ${inputIdentifier}Data = (String) ${inputIdentifier}Wrapped.getPayload();
                </#list>
                    String end="end";
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject= null;
                    try {
                    jsonObject = (JSONObject) jsonParser.parse(sendGet(
                <#list inputIdentifiers as inputIdentifier>
                ${inputIdentifier}Data,
                </#list>end));    // send the HTTP request and parse
                    } catch (ParseException e) {
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    }

                    Set keySet = jsonObject.keySet();
                    Iterator i = keySet.iterator();
                    Map<String, IData> resultMap = new HashMap<String, IData>();
                    while(i.hasNext()){
                    String key= (String)i.next();
                    String value= (String)jsonObject.get(key);
                    IData wrappedValue=null;
                <#list outputIdentifiers as outputIdentifier>
                    <#list outputMappings as outputMapping>
                        <#if outputMapping.identifier = outputIdentifier>
                            if("${outputIdentifier}".equalsIgnoreCase(key)) {
                            wrappedValue=new ${outputMapping.mappingClass}(value);
                            }
                        </#if>
                    </#list>
                </#list>
                    resultMap.put(key,wrappedValue);
                    }
                    return resultMap;
                    }
                    private String sendGet(<#list inputIdentifiers as inputIdentifier>
                    String ${inputIdentifier}Data,
                </#list>String end) throws IOException {
                    url = url+"?end="+ end<#list inputIdentifiers as inputIdentifier>
                    +"&${inputIdentifier}="+${inputIdentifier}Data
                </#list>
                    +"&workflowId=${processName}";
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    // optional default is GET
                    con.setRequestMethod("GET");

                    //add request header
                    con.setRequestProperty("User-Agent", USER_AGENT);

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                    }
                    }
