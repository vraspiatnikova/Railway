$(function () {


    $("#find-trip").validate({
        rules: {
            stationFrom: {
                required: true,
            },
            password: {
                required: true

            }

        },


        // Messages for form validation

        messages: {

            stationFrom: {

                required: 'Please enter station from'

            },

            password: {

                required: 'Please enter password'

            }

        }


    })
});

