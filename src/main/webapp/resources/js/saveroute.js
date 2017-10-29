jQuery(document).on('click', '[name="addWaypoint"]', function () {
    var wprow = jQuery('#routeForm .waypoint-row:last');
    var clone = wprow.clone();
    clone.find('.bootstrap-select').replaceWith(function() { return $('select', this); });
    clone.find('select').selectpicker();
    wprow.after(clone);
    // jQuery('#routeForm .waypoint-row:last').after($('#routeForm .waypoint-row:last').clone());
    // jQuery('#routeForm .waypoint-row:last input').val('');
    // jQuery('#routeForm .waypoint-row:last select').val('');
    if (jQuery('#routeForm .waypoint-row').length > 2) $('[name="delWaypoint"]').removeClass("disabled");
});

jQuery(document).on('click', '[name="delWaypoint"]:not(.disabled)', function () {
    jQuery('#routeForm .waypoint-row:last').remove();
    if (jQuery('#routeForm .waypoint-row').length < 3) $('[name="delWaypoint"]').addClass("disabled");
});

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
    })
});
