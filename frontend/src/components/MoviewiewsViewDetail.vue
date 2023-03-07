<template>

    <v-card outlined>
        <v-card-title>
            Moviewiews # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="MovieId" v-model="item.movieId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Desc" v-model="item.desc" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="ReviewCnt" v-model="item.reviewCnt" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="MovieStatus" v-model="item.movieStatus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="NumberOfSeats" v-model="item.numberOfSeats" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="StartDate" v-model="item.startDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="EndDate" v-model="item.endDate" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MoviewiewsViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/moviewiews/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

