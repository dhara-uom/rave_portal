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

function readKML(){

    var URL = document.getElementById('addKML').value ;
    kml = new kmlClient([URL]);
    vector_layer = kml.loadKML(URL);
    map.addLayers([vector_layer]);

    vector_layer.events.register('featureselected',this,kml.on_select_feature);
    vector_layer.events.register('featureunselected',this,kml.on_unselect_feature);

}


function clearKML(){
    map.removeLayer(vector_layer);
}

