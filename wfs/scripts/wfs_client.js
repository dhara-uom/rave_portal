
function WFSClient(initial_services) {

    this.loadFeatures = function(featureArray){
        this.getCapabilities(this.getCapabilitiesSuccess, this.getCapabilitiesFailure);

    }

    this.addFeaturesToMap = function (){
        var select = document.getElementById("features").selectedIndex;
        var options = document.getElementById("features").options;

        var featureNS = featureArray[options[select].index].featureNS;

        if(featureNS == undefined){
            typename = featureArray[options[select].index].name;
            this.describeFeature(this.describeFeatureTypeSuccess, this.describeFeatureTypeFailure,typeName);
        }
        else{
            this.addLayerToMap(featureArray[options[select].index]);
        }

    }

    this.addLayerToMap = function (feature){
        var select = document.getElementById("services");
        var url = select.options[select.selectedIndex].text;

        var layer = new OpenLayers.Layer.Vector("WFS   "+feature.name, {
            strategies: [new OpenLayers.Strategy.BBOX()],
            protocol: new OpenLayers.Protocol.WFS({
                url:  url,
                featureType: feature.name,
                featureNS: feature.featureNS
            })
        , styleMap: new OpenLayers.StyleMap({
                fillColor: this.get_random_color(),
                strokeWidth: 0.5,
                fillOpacity: .8
            })});

        map.addLayers([layer]);
    }

    this.get_random_color = function()
    {
        var r = (Math.round(Math.random()* 127) + 127).toString(16);
        var g = (Math.round(Math.random()* 127) + 127).toString(16);
        var b = (Math.round(Math.random()* 127) + 127).toString(16);
        return '#' + r + g + b;

    }

    this.getCapabilities = function(targetSuccessFunction, targetFailureFunction){
        var requestXML = "<?xml version='1.0' encoding='UTF-8'?>";
        requestXML +=
            '<wfs:GetCapabilities'+
                ' service="WFS"'+
                ' version="1.0.0"'+
                ' xmlns:wfs="http://www.opengis.net/wfs"'+
                ' xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'+
                ' xsi:schemaLocation="http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.0.0/WFS-basic.xsd"'+
                '/>';

        var options = new Object();

        options.postBody = requestXML;

        var select = document.getElementById("services");
        var url = select.options[select.selectedIndex].text;

        new OpenLayers.Request.POST({
            url: url,
            data: options.postBody,
            headers: {
                "Content-Type": "text/xml;charset=utf-8"
            } ,
            callback:targetSuccessFunction,
            failure: targetFailureFunction
        });
    };

    this.getCapabilitiesFailure = function(){
        this.alert("error","WFS request failed, check your URL.");
    };

    this.getCapabilitiesSuccess = function(response){
        var xml = response;

        var wfsFeatures = [];
        wfsFeatures = parseXML(xml);


        if(wfsFeatures.length == 0){
            alert("error","Parsing the GetCapabilities failed!");
        } else {

            featureArray = wfsFeatures;

            var featureSelect = document.getElementById("features") ;
            if(featureArray.length == 0){
                alert("error","No WFS service in the list.");
                return;
            }

            for(var index=0; index< featureArray.length; index++){
                featureSelect.options[index] = new Option(featureArray[index].name,index);
            }

        }
    };

    this.describeFeature = function (targetSuccessFunction, targetFailureFunction,typeName){

        var requestXML = '<?xml version="1.0" ?>'+
            '<wfs:DescribeFeatureType'+
            ' service="WFS"'+
            ' version="1.0.0"'+
            ' xmlns:wfs="http://www.opengis.net/wfs"'+
            ' xmlns:myns="http://www.example.com/myns"'+
            ' xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'+
            ' xmlns:xsd="http://www.w3.org/2001/XMLSchema"'+
            ' xsi:schemaLocation="http://www.opengis.net/wfs ../wfs/1.0.0/WFS-basic.xsd">'+
            ' <wfs:TypeName>'+typeName+'</wfs:TypeName>'+
            '</wfs:DescribeFeatureType>';

        var options = new Object();

        options.postBody = requestXML;

        var select = document.getElementById("services");
        var url = select.options[select.selectedIndex].text;

        new OpenLayers.Request.POST({
            url: url,
            data: options.postBody,
            headers: {
                "Content-Type": "text/xml;charset=utf-8"
            } ,
            callback:targetSuccessFunction,
            failure: targetFailureFunction
        });
    } ;

    this.describeFeatureTypeSuccess = function(response){

        console.log("valid response        "+response);

    };

    this.describeFeatureTypeFailure = function(response){
        alert("Error sending describeFeature.");
    };

    parseXML = function(xmlDoc){

        var format = new OpenLayers.Format.XML();
        var xml = format.read(xmlDoc.responseText);
        var text = format.write(xml);
        var nodes = [];

        var capformat =  new OpenLayers.Format.WFSCapabilities();
        var cap = capformat.read(xml);
        for(var i=0 ;i<cap.featureTypeList.featureTypes.length;i++){
            nodes[i] = cap.featureTypeList.featureTypes[i];
        }

        return nodes;
    };


}