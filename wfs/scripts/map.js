/**
 * Created with IntelliJ IDEA.
 * User: sameera
 * Date: 6/11/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */

OpenLayers.ProxyHost = widget.proxify("");

var map, wpsClient, wfsClient;

var index = 0;

function init(){

    // create the UI
    var layer = new OpenLayers.Layer.Vector("Scratchpad");
    var toolbar = new OpenLayers.Control.EditingToolbar(layer);
    toolbar.addControls([new OpenLayers.Control.ModifyFeature(layer, {
        title: "Select feature"
    })]);
    map = new OpenLayers.Map('map', {
    //    projection: 'EPSG:3857',
        controls: [
            toolbar,
            new OpenLayers.Control.ZoomPanel(),
            new OpenLayers.Control.PanPanel()
        ],
        layers: [
            new OpenLayers.Layer.WMS(
                "OpenLayers WMS", "http://vmap0.tiles.osgeo.org/wms/vmap0",
                {layers: 'basic'}
            ),
            new OpenLayers.Layer.Google(
                "Google Physical",
                {type: google.maps.MapTypeId.TERRAIN}
            ),
            new OpenLayers.Layer.Google(
                "Google Streets", // the default
                {numZoomLevels: 20}
            ),
            new OpenLayers.Layer.Google(
                "Google Hybrid",
                {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
            ),
            new OpenLayers.Layer.Google(
                "Google Satellite",
                {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22}
            ),new OpenLayers.Layer.WMS(
                "OSM", "http://maps.opengeo.org/geowebcache/service/wms",
                {layers: "openstreetmap", format: "image/png"}
            ),
            layer
        ],
        center: new OpenLayers.LonLat(10.2, 48.9)
            .transform('EPSG:4326', 'EPSG:3857'),
        zoom: 5
    });

    map.addControl(new OpenLayers.Control.LayerSwitcher());
    map.zoomToMaxExtent();

    if(!map.getCenter()){
        map.zoomToMaxExtent();
    }
    if (map.getZoom()<2){
        map.zoomTo(2);
    }


}

    //----------------------------------------------------------------------------------------------------------------------
    //WFS client functions on the page

    function addToList(){

        var URL = document.getElementById("addServiceText").value  ;
        var select= document.getElementById("services")  ;
        select.options[index] = new Option(URL,index);
        select.options[index].selected = 'selected';
        index = index+1;
        wfsClient = new WFSClient([URL]);		// "http://giv-ows2.uni-muenster.de:8080/geoserver/wfs"
        var featureArray = ['tasmania_roads','tasmania_water_bodies','states','tasmania_state_boundries'];
        wfsClient.loadFeatures(featureArray);

    }

    function addToMap(){

        wfsClient.addFeaturesToMap();
    }



