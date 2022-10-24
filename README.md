# __JobAssigntment_BackEnd__
## __Job Assignment API__
<hr/>

### *Summary*
  Your task is to build a Resourcing API using the Java Spring Boot framework, that allows consumers to assign temps to jobs.
<hr/>

### **Endpoints**
<hr/>

- ### jobs
  - POST /jobs - Creates a job
  - PATCH /jobs/{id} - Updates job, endpoint should be used to assign temps to jobs
  - GET /jobs - Fetch all jobs
  - GET /jobs?assigned={true|false} - Filter by whether a job is assigned to a temp or not
  - GET /jobs/{id} - (id, name, tempId, startDate, endDate)
- ### temps
  - POST /temps - Create a temp
  - GET /temps - List all temps
  - GET /temps?jobId={jobId} - List temps that are available for a job based on the jobs date range
  - GET /temps/{id} - get temp by id (should also display jobs they’ve been assigned to)
<hr>

*Example Payloads*
<hr/>


```js
// GET /jobs/{id}
{
	id: ...,
	name: ...,
	startDate: ...,
	endDate: ...,
	temp: {
		id: ...,
		firstName: ...,
		lastName: ...,
	} 
// temp can also be null if a temp hasn't been assigned to the job
}

// GET /temps/{id}
{
	id: ...,
	firstName: ...,
	lastName: ...,
	jobs: [{
		id: ...,
		name: ...,
		startDate: ...,
		endDate: ...,
	}, ...] // can be empty if temp hasn't been assigned to jobs
}
```

### *Assumptions*
<hr/>

- Temps can only have one job at a time (can’t be doing 2 jobs on the same date)
- Temps can have many jobs, and job can have 1 temp assigned
- Should be able to assign existing temps to jobs via POST /jobs & PATCH /jobs/{id}
- You must use a relational database

### *Bonus*
<hr/>

- Temps should be able to manage other temps (will require an additional field)
- When you request a temp record it should display the reports of that temp
- Should be represented in the database as a nested set
- GET /temps/tree - should display the whole tree of temps

<hr/>

### *PROJECT DEMO*
<hr/>

### *TESTING OUT ENDPOINTS IN POSTMAN*

### __Job__
- POST /jobs - Creates a job without Temp
<img width="1792" alt="image" src="https://user-images.githubusercontent.com/93105607/196624038-27760400-26b9-49a3-a641-a1e257b521f2.png">

- POST /jobs - Creates a job with Temp assigned
<img width="1788" alt="image" src="https://user-images.githubusercontent.com/93105607/196628386-f762acb5-aaaa-477a-8022-e4d9e45d9351.png">

- PATCH /jobs/{jobId} - Updates the fields depends on header request
<img width="1786" alt="image" src="https://user-images.githubusercontent.com/93105607/196624518-9ab7ba15-8243-4da2-b727-2b765081b860.png">

- PATCH /jobs/{jobId} - Assign temp to job
<img width="1794" alt="image" src="https://user-images.githubusercontent.com/93105607/196627532-f49271d7-6bfd-4219-b85b-9f5534057b64.png">

- GET /jobs - Get all jobs
<img width="1795" alt="image" src="https://user-images.githubusercontent.com/93105607/196625582-064c733e-a9dc-496c-b82e-14f2585de026.png">

- GET /jobs?assgined={true|false} Get jobs assigned or unassigned
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/93105607/196628696-0127b73a-2580-4b96-bd39-cbe1fea6edbc.png">
<img width="1796" alt="image" src="https://user-images.githubusercontent.com/93105607/196628762-38c4bfde-7853-4ca0-8f0e-142efc50c9bd.png">

- GET /jobs/{jobId} - Get a job by Id
<img width="1795" alt="image" src="https://user-images.githubusercontent.com/93105607/196625814-ac557e2b-f967-4e23-91e8-72b0c50f03aa.png">
<hr/>

### __Temp__
- POST /temps - Creats a temp
<img width="1794" alt="image" src="https://user-images.githubusercontent.com/93105607/196626526-af481050-c168-4779-8826-8bc4ea750d3d.png">

- GET  /temps - Get all temps
<img width="1791" alt="image" src="https://user-images.githubusercontent.com/93105607/196626924-d8b6c931-4e59-4aa6-829c-b2152f50dccd.png">

- GET  /temps?jobid={jobid} - Get available temps to be assigned for given jobId
<img width="1784" alt="image" src="https://user-images.githubusercontent.com/93105607/196660255-eaf30bf4-7a20-407d-ba5e-6eda93b288c0.png">

- GET  /temps/1 - Get a temp by its id
<img width="1796" alt="image" src="https://user-images.githubusercontent.com/93105607/196627344-7411e793-271c-4886-a6c3-8e681d4cdf9b.png">

<hr/>

### *Future goals / Improvement*

<hr/>

- Use Spring Security for authentication / authorization implementation.
- Create Front-End Using React and connect with it to make a full stacked app in the end.
  
  


