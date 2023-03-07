<template>

    <v-data-table
        :headers="headers"
        :items="moviewiews"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MoviewiewsView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "movieId", value: "movieId" },
                { text: "desc", value: "desc" },
                { text: "reviewCnt", value: "reviewCnt" },
                { text: "movieStatus", value: "movieStatus" },
                { text: "numberOfSeats", value: "numberOfSeats" },
                { text: "startDate", value: "startDate" },
                { text: "endDate", value: "endDate" },
            ],
            moviewiews : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/moviewiews'))

            temp.data._embedded.moviewiews.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.moviewiews = temp.data._embedded.moviewiews;
        },
        methods: {
        }
    }
</script>

