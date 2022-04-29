# Learn Spring Boot

Structure:

- entity folder: used to map database
- model folder: used for controller payload, etc

Relations:

- Course to CourseMaterial 1:1, in course_materials table, there will be `course_id` as foreign key referencing courses
  table.
- Teacher to Course: 1:N

## Issues

- How to fix course lazy fetch type?
- Nested Json response solved by `JsonIgnore`, but requesting courseMaterials won't include course anymore, making the
  FetchType not work.