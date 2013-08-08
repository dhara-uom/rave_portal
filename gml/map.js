/**
 * Created with IntelliJ IDEA.
 * User: sameera
 * Date: 7/3/13
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */

var map, vector_layer,select_feature_control,kml, gml, gml_vector;

function init(){
    //initialize map
    map = new OpenLayers.Map("map_element_1", {controls:[new OpenLayers.Control.Navigation(),
        new OpenLayers.Control.PanZoomBar(), new OpenLayers.Control.OverviewMap()]});

    //initialize two map layers
    var google_map = new OpenLayers.Layer.Google('Google layer',{});

    var google_hybrid_map= new OpenLayers.Layer.Google('Google Hybrid layer', {type:google.maps.MapTypeId.HYBRID});

    var gml_layer = new OpenLayers.Layer.WMS( "OpenLayers WMS",
        "http://vmap0.tiles.osgeo.org/wms/vmap0", {layers: 'basic'} );
    map.addLayers([gml_layer,google_map, google_hybrid_map]);
    map.addControl(new OpenLayers.Control.LayerSwitcher());



    if(!map.getCenter()){
        map.zoomToMaxExtent();
    }
    if (map.getZoom()<2){
        map.zoomTo(2);
    }


}

function readGML(){

    var URL = document.getElementById("addGML").value;
    gml = new gmlClient();
    gml_vector = gml.loadGML(URL);
    map.addLayers([gml_vector]) ;

}

function clearGML(){
    map.removeLayer(gml_vector);
}
