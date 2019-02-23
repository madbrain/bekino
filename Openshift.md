
# Create Project

* Name: `bekino`
* Display name: `BeKino`

# Add Database

* Add to Project
* Category: `Databases/MongoDB`
* Template: `MongoDB`
* MongoDB Database Name: `bekino`

# Insert Data

* Go to `Applications/Pods` and select `mongo-*` pod
* Go to terminal and run command `mongo -u $MONGODB_USER -p $MONGODB_PASSWORD $MONGODB_DATABASE`
* Then do some mongo insert:
```
db.theater.insert([
    { name: "Comoedia", allocineCode: "P3757" },
    { name: "UGC Confluence", allocineCode: "W6902" }
])
```

And test:
```
db.theater.find( { allocineCode: "W6902" })
db.theater.find( { name: { $regex: /^Co*/ } })
```

## Objects:
- Secret
- PersistentVolumeClaims
- DeploymentConfig
- Service

# Add Application

* Category: `Languages/Java`
* Template: `OpenJDK 8`

* Application Name: `bekino-app`
* Git Repository URL: `https://github.com/madbrain/bekino.git`
* Context Directory: `<empty>`

# Customize deployment config

## Add environment variables

* Go to `Applications/Deployments` and select `bekino-app`
* Go to `Environment` and add:
  - `OPENSHIFT_MONGODB_DB_HOST` = `mongodb`
  - `OPENSHIFT_MONGODB_DB_PORT` = `27017`
  - `OPENSHIFT_MONGODB_DB_USERNAME` = secret: `mongodb` key: `database-user`
  - `OPENSHIFT_MONGODB_DB_PASSWORD` = secret: `mongodb` key: `database-password`

## Add health check

* Use `Actions/Edit Health Checks`
* Add `Liveness Probe` with parameters :
  - Port: `8080`
  - Initial Delay: `20`
  - Timeout: `5`

## Objects:
- BuildConfig
- DeploymentConfig
- Service
- Route

# Customize build config

[Add incremental option](https://docs.openshift.com/online/dev_guide/builds/build_strategies.html#incremental-builds)

Add webhook into GitHub