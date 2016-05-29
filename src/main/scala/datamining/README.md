## Data mining exercise

Queries
---

- Count the duplicates and the unique rows - since Cassandra doesn't provide a `group by` function
we can leverage on user defined aggregations to make ourselves one

```cql
CREATE FUNCTION exercise.counByObsrvWeek(state map<int, int>, obsrvweek int)
RETURNS NULL ON NULL INPUT
RETURNS map<int, int>
LANGUAGE java
AS '
if(state.containsKey(obsrvweek)) {
   Integer currentCount = (Integer)state.get(obsrvweek);
   state.put(obsrvweek, currentCount + 1);
} else {
   state.put(obsrvweek, 1);
}
return state;
';

CREATE AGGREGATE groupByObsrvweekAndCount(int)
SFUNC counByObsrvWeek
STYPE map<int, int>
INITCOND {};
```

This can be used as such:

```cql
select groupByObsrvweekAndCount(observation_week) FROM observations;
```

