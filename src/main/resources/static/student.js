/**
 * This is the JavaScript file that the main page uses.
 */

// -- Global Variables and Main Code --
var currentSelectedStudentID = -1 // The ID of the currently selected student
var allStudents = loadStudentDataFromHTML() // An array of all the students, retrieved from the HTML table

// Draw the rectangles for each student
allStudents.forEach((student) => {
    drawStudentRectangle(student)
})

// -- Functions --
/**
 * Called when the user clicks on a student's Select button.
 */
function selectedStudent(button) {
    currentSelectedStudentID = button.getAttribute("sid")
    updateHtmlAfterSelectingStudent()
    console.log("Current Selected Student ID: ", currentSelectedStudentID)
}

/**
 * Updates the HTML to reflect the currently selected student.
 */
function updateHtmlAfterSelectingStudent() {
    var editForm = document.querySelector(".edit-selected-student-form")
    // Changes the form's action to be editing the selected student
    editForm.action = "/students/edit/" + currentSelectedStudentID
    editForm.elements["name"].value = allStudents.find((student) => student.sid == currentSelectedStudentID).name
    editForm.elements["weight"].value = allStudents.find((student) => student.sid == currentSelectedStudentID).weight
    editForm.elements["height"].value = allStudents.find((student) => student.sid == currentSelectedStudentID).height
    editForm.elements["hairColor"].value = allStudents.find((student) => student.sid == currentSelectedStudentID).hairColor
    editForm.elements["gpa"].value = allStudents.find((student) => student.sid == currentSelectedStudentID).gpa

    document.querySelectorAll(".selected-student").forEach((element) => {
        element.innerHTML = currentSelectedStudentID
    })
}

/**
 * Draws a rectangle for the given student.
 */
function drawStudentRectangle(student) {
    // Create the rectangle
    var rectangle = document.createElement("div")
    rectangle.className = "d-flex p-2 border border-dark m-2"
    rectangle.style.flexGrow = 0 // Prevent the rectangle from growing
    rectangle.style.flexShrink = 0 // Prevent the rectangle from shrinking
    rectangle.style.width = student.weight * 1.5 + "px" // Set a fixed width
    rectangle.style.height = student.height + "px" // Set a fixed height

    // Add the student's information
    var info = document.createElement("p")
    var name = document.createElement("strong")
    name.style.color = student.hairColor // Set the color of the name to the student's hair color
    name.textContent = student.name
    info.appendChild(name)
    info.innerHTML += `<br>GPA: ${student.gpa}`
    rectangle.appendChild(info)

    // Add the rectangle to the container
    var container = document.getElementById("student-rectangle-container")
    container.appendChild(rectangle)
}

/**
 * Loads the student data from the HTML table and returns it as an array of objects.
 */
function loadStudentDataFromHTML() {
    var tableRows = document.querySelectorAll("#student-table tbody tr")
    var allStudents = []

    tableRows.forEach(function (row) {
        var student = {
            sid: row.cells[0].innerText,
            name: row.cells[1].innerText,
            weight: row.cells[2].innerText,
            height: row.cells[3].innerText,
            hairColor: row.cells[4].innerText,
            gpa: row.cells[5].innerText,
        }

        allStudents.push(student)
    })

    return allStudents
}
