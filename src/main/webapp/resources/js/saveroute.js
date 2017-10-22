jQuery(document).on('click', '[name="saveRoute"]', function () {
    var pst = {};
    pst.routeNumber = jQuery('[name="routeNumber"]').val();
    pst.waypoints = [];
    jQuery('.waypoint-row').each(function () {
        pst.waypoints.push({
            station: jQuery(this).find('[name="stations"]').val(),
            travelTime: jQuery(this).find('[name="travelTime"]').val(),
            stopTime: jQuery(this).find('[name="stopTime"]').val()
        })
    });
    console.log(pst);
    var json = JSON.stringify(pst);
    jQuery.post('saveroute', {json: json}, function (data) {
        jQuery('#routeForm').after(data);
        // window.location.href="/allRoutes"
    })
});