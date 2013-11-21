  //initialize global variables
var map, vector_layer,select_feature_control;


function init(){
    //initialize map
    map = new OpenLayers.Map("map_element_1", {controls:[new OpenLayers.Control.Navigation(),
        new OpenLayers.Control.PanZoomBar(), new OpenLayers.Control.OverviewMap()]});

    //initialize two map layers
    var google_map = new OpenLayers.Layer.Google('Google layer',{});

    var google_hybrid_map= new OpenLayers.Layer.Google('Google Hybrid layer', {type:google.maps.MapTypeId.HYBRID});

    //initialize vector layer to read and display KML data
    vector_layer = new OpenLayers.Layer.Vector("KML", {
        strategies: [new OpenLayers.Strategy.Fixed(),new OpenLayers.Strategy.Cluster()],
        protocol: new OpenLayers.Protocol.HTTP({
            url: "/portal/static/scripts/world_earth_quake_2013.kml",
            format: new OpenLayers.Format.KML({
                extractAttributes: true,
                extractStyles:true,
                maxDepth: 2
            })
        })
    })



    var vectore_style = new OpenLayers.Style({'fillColor':'#669933','fillOpacity':.8,'fontColor':'#f0f0f0',
            'fontFamiy':'arial,sans-serif','fontSize':'.9em', 'fontWeight':'bold','label':'${num_points}',
            'pointRadius':'${point_radius}','strokeColor':'#aaee77','strokeWidth':3},
        {context:{num_points:function(feature){return feature.attributes.count},
            point_radius:function(feature){return 9+(feature.attributes.count)}}});

    var vector_style_select = new OpenLayers.Style({'fillColor':'#F08080','fillOpaciy':.9,'fontColor':'#232323',
        'strokeColor':'#8B0000'});

    var style_map = new OpenLayers.StyleMap    ({'default':vectore_style   ,'select':vector_style_select });

    select_feature_control = new OpenLayers.Control.SelectFeature(vector_layer,{});

    vector_layer.styleMap = style_map ;
    vector_layer.setVisibility(true);

    //add event lisnters to vector layer
    vector_layer.events.register('featureselected',this,on_select_feature);
    vector_layer.events.register('featureunselected',this,on_unselect_feature);

    //add layers, controls to map
    map.addLayers([google_map,vector_layer,google_hybrid_map]);
    map.addControl(select_feature_control );
    map.addControl(new OpenLayers.Control.LayerSwitcher());
    select_feature_control.activate();


    if(!map.getCenter()){
        map.zoomToMaxExtent();
    }


}

 //on feature select
function on_select_feature(event){

    var content='';

    var cluster= event.feature.cluster;
    //if feature contains several cordinates, loop to read all
    for(var i=0;i<cluster.length;i++){

        content +='<strong>'+cluster[i].attributes.name+'</strong>'+'<br/>'+"<img src='"+
            cluster[i].style.externalGraphic+"'/>"+cluster[i].attributes.description+'<br/><hr/>';

    }

    var feature = event.feature;
    //initialize a popup window
    var popup = new OpenLayers.Popup.FramedCloud("chicken",
        feature.geometry.getBounds().getCenterLonLat(),
        new OpenLayers.Size(100,100),
        content,
        null, true, onPopupClose);
    feature.popup = popup;
    map.addPopup(popup);

}

function on_unselect_feature(event){
//if a feature unselected, close popup
    var feature = event.feature;
    if(feature.popup) {
        map.removePopup(feature.popup);
        feature.popup.destroy();
        delete feature.popup;
    }
}

function onPopupClose(evt) {
    select_feature_control.unselectAll();
}

