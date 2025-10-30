---
layout: default.md
title: User Guide
pageNav: 3
---

<style>
details summary { cursor: pointer; list-style: none; }
details summary h2 { display: inline; margin: 0; }

details summary h2::after {
  content: " (click to expand)";
  font-weight: 400;
  font-size: 0.95rem;
  color: #6b7280;
  margin-left: .15rem;
}

details[open] summary h2::after {
  content: " (click to collapse)";
}

details table { margin-top: .5rem; }
</style>

<style>
.nav-tabs { 
  border-bottom: 2px solid #e5e7eb;
  margin-top: .5rem;
}
.nav-tabs .nav-link {
  padding: .5rem .9rem;
  font-weight: 600;
  color: #374151;
  border: 1px solid transparent;
  border-radius: .6rem .6rem 0 0;
}
.nav-tabs .nav-link:hover {
  color: #111827; 
  background: #ffecb8;
}
.nav-tabs .nav-link.active {
  color: #111827;
  background: #ffcb3b;
  border-color: #e5e7eb #e5e7eb #ffcb3b;
}

.tab-content {
  border: 1px solid #ffcb3b;
  border-top: none;
  border-radius: 0 .6rem .6rem .6rem;
  padding: 1rem;
  background: #fff9e8;
  box-shadow: 0 1px 0 rgba(0,0,0,.02);
}
</style>

<script>
(function () {
  const idToTab = {
    'general-parameters': 'General',
    'contact-parameters': 'Contact',
    'interview-parameters': 'Interview'
  };

  function activateTabFor(id) {
    const wanted = idToTab[id];
    if (!wanted) return;
    const tabs = Array.from(document.querySelectorAll('.nav-tabs .nav-link'));
    const target = tabs.find(a => a.textContent.trim().toLowerCase().includes(wanted.toLowerCase()));
    if (target && !target.classList.contains('active')) target.click();
  }

  function handleHash() {
    const id = (location.hash || '').slice(1);
    if (id) activateTabFor(id);
  }

  // Switch on TOC click (instant) and on hashchange (fallback)
  document.addEventListener('click', (e) => {
    const a = e.target.closest('a[href^="#"]');
    if (!a) return;
    const id = a.getAttribute('href').slice(1);
    activateTabFor(id);
  });

  window.addEventListener('hashchange', handleHash);
  document.addEventListener('DOMContentLoaded', handleHash);
})();
</script>


<style>
  .visually-hidden-anchor {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  padding: 0;
  overflow: hidden;
  clip: rect(0 0 0 0);
  clip-path: inset(50%);
  border: 0;
}

html { scroll-behavior: smooth; }

#general-parameters,
#contact-parameters,
#interview-parameters {
  scroll-margin-top: 80px;
}
</style>

<style>
/* Roadmap grid + cards */
.roadmap-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: .9rem;
  margin-top: .75rem;
}
.roadmap-card {
  background: #fffdf5;
  border: 1px solid #ffe082;
  border-radius: .75rem;
  padding: 1rem;
  box-shadow: 0 1px 0 rgba(0,0,0,.03);
}
.roadmap-card h4 {
  display: flex; align-items: center; gap: .5rem;
  margin: 0 0 .4rem 0;
  font-size: 1.05rem;
}
.roadmap-meta {
  display: flex; flex-wrap: wrap; gap: .5rem; margin: .4rem 0 .6rem 0;
  font-size: .85rem; color: #4b5563;
}
.chip {
  display: inline-block;
  padding: .15rem .5rem;
  border-radius: 999px;
  font-weight: 600;
  font-size: .75rem;
  border: 1px solid;
}
.chip.planned    { color:#8a6d00; background:#fff7cc; border-color:#ffe082; }
.chip.proposed  { color:#1f2937; background:#e5e7eb; border-color:#e5e7eb; }

.why, .you-can {
  margin: .5rem 0 0 0; padding-left: 1.1rem;
}
.roadmap-actions {
  margin-top: .7rem; display:flex; gap:.5rem; flex-wrap:wrap;
}
.btn-ghost {
  border:1px dashed #ffcb3b; color:#7c5b00; background:#fffaf0;
  padding:.3rem .6rem; border-radius:.6rem; font-size:.85rem; text-decoration:none;
}
</style>

![PressPalAbout](images/PressPalAbout.png)

## About

PressPal is designed to streamline contact and interview management for breaking news reporters. Traditional address books and note-taking tools are not optimized for the high-speed, high-pressure environment of news reporting, where reporters must manage hundreds of contacts across multiple sources (phones, emails, notebooks).

The **goal of PressPal** is to:
- Provide reporters with a single command-driven system for organizing and retrieving contacts quickly.
- Enable fast entry and retrieval of context (interview history, organization, role, notes) while working on multiple stories simultaneously.
- Support the entire lifecycle of a contact — from initial outreach, to active follow-up, to archiving once a story concludes.
  Ultimately, PressPal aims to reduce cognitive load for reporters, allowing them to focus on storytelling while ensuring no lead, contact, or follow-up is lost.

## Table of Contents
1. [Quick Start](#quick-start)
2. [Input Parameters](#input-parameters)
    - [General Parameters](#general-parameters)
    - [Contact Parameters](#contact-parameters)
    - [Interview Parameters](#interview-parameters)
3. [Features](#features)
    - **General Commands**
      - [Viewing help : `help`](#viewing-help-help)
      - [Clearing all entries : `clear`](#clearing-all-entries-clear)
      - [Exiting the program : `exit`](#exiting-the-program-exit)
    - **Contact Management Commands**
      - [Adding a person: `add`](#adding-a-person-add)
      - [Deleting a person : `delete`](#deleting-a-person-delete)
      - [Editing a person : `edit`](#editing-a-person-edit)
      - [Listing all persons : `list`](#listing-all-persons-list)
      - [Locating persons by name, organisation, role, or categories : `find`](#locating-persons-by-name-organisation-role-or-categories-find)
    - **Interview Management Commands**
      - [Adding an interview to a contact : `addInterview`](#adding-an-interview-to-a-contact-addinterview)
      - [Deleting an interview from a contact : `deleteInterview`](#deleting-an-interview-from-a-contact-deleteinterview)
      - [Listing all interviews of a contact : `listInterview`](#listing-all-interviews-of-a-contact-listinterview)
      - [Display the upcoming interview : `nextInterview`](#display-the-upcoming-interview-nextinterview)
    - **Category Management Commands**
      - [Add category(s) to a person : `addCat`](#add-categorys-to-a-person-addcat)
      - [Delete category(s) from a person : `deleteCat`](#delete-categorys-from-a-person-deletecat)
      - [Saving the data](#saving-the-data)
4. [Frequently Asked Questions (FAQ)](#frequently-asked-questions-faq)
    - [General Q&A](#faq-general-qa)
    - [Common Problems & Fixes](#faq-common-problems)
    - [Troubleshooting](#faq-troubleshooting)
    - [Feature Rationale](#faq-feature-rationale)
7. [Future Iteration Plans](#future-iteration-plans)
8. [Command summary](#command-summary)

### User Profiles
- Users with basic familiarity with Windows/macOS/Linux file navigation and opening a terminal.
- Users who favor CLI over GUI and type quickly.
- Users who manage an extensive network of contacts and numerous scheduled interviews to monitor.

### System Requirements
- Java 17 (or newer) installed and available on the PATH.
- Operating System: Windows, macOS, or Linux.

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer.<br>
   - **Windows & Linux Users:** Download and install Java from [Oracle JDK 17 Archive Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
     - **Windows Users:** Follow the instructions [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html).
   - **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).<br/><br/>

2. **Download our app:**
   - Install the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-W08-1/tp/releases/latest).<br/><br/>

3. **Prepare a folder:**
   - Copy the file to the folder you want to use as the _home folder_ for PressPal.<br/><br/>

4. **Launch the app:** 
   - Open a terminal
   - Navigate to the folder where you copied the `.jar` file to. 
     - For **Windows Users**:
       - Open the Command Prompt and type `cd` followed by the folder path, then press Enter.
     - For **macOS/Linux Users**:
       - Open a terminal and type `cd` followed by the folder path, then press Enter.
   - Launch the app by entering the following command:<br/>
     `java -jar PressPal.jar`
   - A GUI like the one below should appear with some sample data:
     ![Ui](images/Ui.png)<br/><br/>

5. **Using the app:** Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Here are some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com r/Student o/NUS c/friends c/owesMoney` : Adds a contact named `John Doe` to the Contact Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. **Learning more about the App:**
   * Refer to the [Features](#features) below for more details about the capabilities of PressPal.<br/><br/>

--------------------------------------------------------------------------------------------------------------------

<details open>
  <summary><h2>Input Parameters</h2></summary>

<panel header=":information_source: Notes about parameters" type="info" expanded>

- All parameters will be in the form of `p/PARAMETER` where `p/` is the symbol designated for that parameter.
  - For Example, the command `listInterview i/1`
    - `i/` is the parameter symbol.
    - `1` is the parameter.

</panel>

<h3 id="general-parameters" class="visually-hidden-anchor">General Parameters</h3>
<h3 id="contact-parameters" class="visually-hidden-anchor">Contact Parameters</h3>
<h3 id="interview-parameters" class="visually-hidden-anchor">Interview Parameters</h3>

<tabs>

  <tab header="🧭 **General**" active>
  <h3>General Parameters</h3>

| Symbol |     Parameter     | Constraints                                                                                                      |
|:------:|:-----------------:|------------------------------------------------------------------------------------------------------------------|
|   `i/`   |  `PERSON_INDEX`   | <ul><li><strong>Positive integer</strong> (1, 2, …).</li><li>Must exist in the current displayed list.</li></ul> |
|   `v/`   | `INTERVIEW_INDEX` | <ul><li><strong>Positive integer</strong> (1, 2, …).</li><li>Must exist in the current displayed list.</li></ul> |
|   `NA`   | `KEYWORD` | <ul><li>Non-empty string.</li></ul>                                                                              |

  </tab>
  <tab header="👤 **Contact**">
  <h3>Contact Parameters</h3>

| Symbol |   Parameter    | Constraints |
|:------:|:--------------:|----------------------------------------------------------------------|
|   `n/`   |     `NAME`     | <ul><li>Non-empty string.</li></ul> |
| `o/` | `ORGANISATION` | <ul><li>Alphanumeric words with single spaces, 1–50 characters.</li></ul> |
| `r/` | `ROLE`         | <ul><li>Alphanumeric words with single spaces, 1–50 characters.</li></ul> |
|   `e/`   |    `EMAIL`     | <ul><li>Format: <code>local-part@domain</code>.</li><li><strong>Local-part</strong>: letters/digits plus <code>+</code>, <code>_</code>, <code>.</code>, <code>-</code>; cannot start or end with a special character.</li><li><strong>Domain</strong>: one or more labels separated by dots.</li><li>Each label starts and ends with an alphanumeric character.</li><li>Within a label, only letters/digits and hyphens (<code>-</code>) are allowed.</li><li>Final label (TLD) must be at least <strong>2 characters</strong> long.</li></ul> |
|   `p/`   |    `PHONE`     | <ul><li>Minimum of 3 digits.</li></ul> |
| `c/` | `CATEGORY` | <ul><li>Alphanumeric, no spaces, 1–20 characters.</li></ul> |

  </tab>
  <tab header="📅 **Interview**">
  <h3>Interview Parameters</h3>

| Symbol | Parameter | Constraints                                                                        |
|:------:|:---------:|------------------------------------------------------------------------------------|
|   `h/`   | `HEADER`  | <ul><li>Non-empty string.</li></ul>                                                |
|   `d/`   | `DATE`    | <ul><li>Format: <code>yyyy-MM-dd</code> (e.g., <code>2025-10-15</code>).</li></ul> |
|   `t/`   | `TIME`    | <ul><li>Format: 24-hour <code>HH:mm</code> (e.g., <code>18:30</code>).</li></ul>   |
|   `l/`   | `LOCATION`| <ul><li>Non-empty string.</li></ul>                                                |

  </tab>

</tabs>

</details>

--------------------------------------------------------------------------------------------------------------------

## Features

<panel header=":information_source: Notes about the command format:" type="info" expanded>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [c/CATEGORY]` can be used as `n/John Doe c/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[c/CATEGORY]…​` can be used as ` ` (i.e. 0 times), `c/friend`, `c/friend c/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

</panel>

### Viewing help : `help`
Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

> **Format**
> ```
> help
> ```

### Clearing all entries : `clear`

Clears all entries from the contact book.

**:warning: Caution:** This action is destructive and cannot be undone.

> **Format**
> ```
> clear
> ```

### Exiting the program : `exit`

Exits the program.

> **Format**
> ```
> exit
> ```

### Adding a person : `add`

Adds a person to the contact book.
* You must provide at least one of `PHONE` and `EMAIL`.
* `PHONE` and `EMAIL` do not have to be unique. Different people can share the same contact details.

> **Format**
> ```
> add n/NAME p/PHONE_NUMBER e/EMAIL o/ORGANISATION r/ROLE [c/CATEGORY]
> ```

<div markdown="span" class="alert alert-primary">

:bulb: Tip:
A person can have any number of categories (including 0).
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com o/NUS r/Student c/friends c/owesMoney`
* `add n/Betsy Crowe p/12345678 e/betsycrowe@example.com o/Reuters r/Reporter`

### Deleting a person : `delete`

Deletes the specified person from the contact book.

> **Format**
> ```
> delete i/INDEX
> ```

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete i/2` deletes the 2nd person in the contact book.
* `find Betsy` followed by `delete i/1` deletes the 1st person in the results of the `find` command.

### Editing a person : `edit`

Edits an existing person in the contact book.

> **Format**
> ```
> edit i/INDEX [n/NAME] [p/PHONE] [e/EMAIL] [o/ORGANISATION] [r/ROLE]
> ```

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit i/1 p/91234567 e/johndoe@example.com` Edits the phone number and email of the 1st person to be `91234567` and `johndoe@example.com` respectively.

### Listing all persons : `list`

Shows a list of all persons in the contact book.

> **Format**
> ```
> list
> ```

### Locating persons by name, organisation, role, or categories : `find`

Finds persons whose name, organisation, role or categories contain any of the given keywords.

> **Format** 
> ```
> find KEYWORD [MORE_KEYWORDS]
> ```

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name, organisation, role and categories are searched. Other fields such as phone or email are not included.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `find John` returns `john` and `John Doe`.
* `find bernice charlotte` returns `Bernice Yu`, `Charlotte Oliveiro`.<br>
  ![result for 'find bernice charlotte'](images/findBerniceCharlotteResult.png)
* `find NUS colleagues` Returns all persons whose details match the keyword of `NUS` or `colleagues`.

### Adding an interview to a contact : `addInterview`

Adds an interview to a contact in the contact book.

> **Format**
> ```
> addInterview i/INDEX h/HEADER d/DATE t/TIME l/LOCATION
> ```

* Adds an interview to the contact at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* The `DATE` must be in the format `YYYY-MM-DD`. e.g. `2025-10-10`.
  * When adding an interview, the `DATE` can be in the past.
* The `TIME` must be in the format `HH:MM`. e.g. `14:30`. 
  * Each contact cannot have more than one interview at the same time. However, different contacts can have interviews at the same time.
* The `LOCATION` can be any text describing where the interview will take place. 
  * Different interviews can take place at the same `LOCATION`.
* The `HEADER` can be any text describing the interview (e.g company name, role, etc.). 
  * Different interviews can have the same `HEADER`.

Examples:
* `addInterview i/1 h/Interview with ABC Corp d/2024-10-10 t/14:00 l/123, Business St, #02-25` adds an interview with header `Interview with ABC Corp`, date `2024-10-10`, time `14:00` and location `123, Business St, #02-25` to the 1st contact in the contact book.

### Deleting an interview from a contact : `deleteInterview`

Deletes an interview from a contact in the contact book.

> **Format** 
> ```
> deleteInterview i/PERSON_INDEX v/INTERVIEW_INDEX
> ```

* Deletes the interview at the specified `INTERVIEW_INDEX` from the contact at the specified `PERSON_INDEX`. The indices refer to the index numbers shown in the displayed person list and interview list respectively. The indices **must be positive integers** 1, 2, 3, …​

Examples:
* `deleteInterview i/1 v/2` Deletes the 2nd interview from the 1st contact in the contact book.

### Listing all interviews of a contact : `listInterview`

Lists all interviews of a contact in the contact book.

> **Format** 
> ```
> listInterview i/INDEX
> ```

* Lists all interviews of the contact at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* If the contact has no interviews, a message indicating so will be shown instead.

![empty interview](images/emptyInterviewExample.png)
![filled interview](images/filledInterviewExample.png)

### Display the upcoming interview : `nextInterview`

Displays the next scheduled interview that occurs at or after the current date and time, excluding any interviews already in the past.
* If the next scheduled interview occurs for more than one contact, they will all be displayed.

> **Format**
> ```
> nextInterview
> ```

Examples:
* `nextInterview` Displays the most upcoming scheduled interview "[Meta Interview] on 15 Oct 2050 2:30PM at Meta HQ."

### Add category(s) to a person : `addCat`

Add category(s) to a person identified by the index number used in the displayed person list.

> **Format** 
> ```
> addCat i/INDEX [c/CATEGORY]...
> ```

* If category A is already added to a person, any attempt to add category A again to the person will be rejected with an error message.

Examples:
* `addCat i/1 c/emergency` Adds the category `emergency` to the person with index 1.
* `addCat i/2 c/emergency c/singapore` Adds the categories `emergency` and `singapore` to the person with index 2.

### Delete category(s) from a person : `deleteCat`

Delete category(s) from a person identified by the index number used in the displayed person list.

> **Format** 
> ```
> deleteCat i/INDEX [c/CATEGORY]...
> ```

Examples:
* `deleteCat i/1 c/emergency` Deletes the category `emergency` from the person with index 1.
* `deleteCat i/2 c/emergency c/singapore` Deletes the categories `emergency` and `singapore` from the person with index 2.

### Saving the data

ContactBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## Frequently Asked Questions (FAQ)

<tabs>

  <tab header="🧠 General Q&A" active>
  <h3 id="faq-general-qa">General Q&A</h3>

**Q:** <u>How do I move my data to another computer?</u>  
**A:** Install the app on the new computer, then replace its empty data file with the one from your old PressPal home folder.

**Q:** <u>Do I need to save my work?</u>  
**A:** Nope. Changes save automatically after each command.

**Q:** <u>How do I update the app?</u>  
**A:** Download the latest `.jar` from [here](https://github.com/AY2526S1-CS2103T-W08-1/tp/releases/latest) and replace your old one. Your data file stays intact.

**Q:** <u>Why didn’t a command work?</u>  
**A:** Check the format and parameter symbols (like `n/`, `e/`, `o/`). See the “Input Parameters” section/tabs above for the exact rules.

**Q:** <u>Can I use PressPal offline?</u>  
**A:** Yes. Everything runs locally; no internet is needed after download.

**Q:** <u>Is my data private?</u>  
**A:** Your data stays on your computer. We don’t sync it anywhere.
</tab>

  <tab header="🛠️ Common Problems & Fixes">
  <h3 id="faq-common-problems">Common Problems and Fixes</h3>

1. **App opens off-screen after unplugging a monitor**  
   – **Why:** The app remembers the last screen position, even if that screen isn’t there anymore.  
   – **Fix:** Delete `preferences.json`, then reopen the app.

2. **Help window won’t reappear after minimizing**  
   – **Why:** The original Help window is still minimized.  
   – **Fix:** Restore the minimized window manually.
   </tab>

  <tab header="🧩 Troubleshooting">
  <h3 id="faq-troubleshooting">Troubleshooting</h3>

**Q:** <u>I found a bug, how do I report it?</u>  
**A:** Tell any developer, their emails are listed [here](AboutUs.md).

**Q:** <u>I have an idea, where do I suggest it?</u>  
**A:** Share it with any developer, contacts are found [here](AboutUs.md).
</tab>

  <tab header="💡 Feature Rationale">
  <h3 id="faq-feature-rationale">Feature Rationale</h3>

**Q:** <u>Why does `addInterview` allow past dates?</u>  
**A:** So you can record past interviews for reference.

**Q:** <u>Why can I schedule two interviews at the same time?</u>  
**A:** Different people may attend the same session, so overlaps across contacts are allowed.

**Q:** <u>Why does `nextInterview` show only one person even if a few interviews share the same time?</u>  
**A:** To keep things fast and clear. `nextInterview` gives you one “next up” so you can act right away.  
If several interviews have the same time, we show the **first one in your current list**. The others are still there and you view them as usual (e.g., `listInterview i/…`).
</tab>

</tabs>

--------------------------------------------------------------------------------------------------------------------

## Future Iteration Plans

<panel header="🚧 Roadmap (what’s coming next)" type="warning" expanded>

<div class="roadmap-grid">

  <div class="roadmap-card">
    <h4>🗄️ Archive contacts & interviews <span class="chip planned">Planned</span></h4>
    <div class="roadmap-meta">
      <span>Goal: keep the main list clutter-free</span>
    </div>
    <strong>Why it matters</strong>
    <ul class="why">
      <li>Old sources pile up and hide the ones you’re working with now.<br/><br/></li>
    </ul>
    <strong>You’ll be able to:</strong>
    <ul class="you-can">
      <li>Move contacts/interviews to an <em>Archive</em> with one command.</li>
      <li>Search and restore archived items anytime.</li>
    </ul>
    <div class="roadmap-actions">
      <a class="btn-ghost" href="#locating-persons-by-name-organisation-role-or-categories-find">Related: search (`find`)</a>
    </div>
  </div>

  <div class="roadmap-card">
    <h4>📵 Enforce unique phone & email <span class="chip proposed">Proposed</span></h4>
    <div class="roadmap-meta">
      <span>Goal: reduce accidental duplicates</span>
    </div>
    <strong>Why it matters</strong>
    <ul class="why">
      <li>Duplicate entries waste time and cause mistakes during outreach.<br/><br/></li>
    </ul>
    <strong>You’ll be able to:</strong>
    <ul class="you-can">
      <li>Get a clear prompt when a number/email already exists.</li>
      <li>Choose to open the existing contact or continue (if intentional).</li>
    </ul>
    <div class="roadmap-actions">
      <a class="btn-ghost" href="#adding-a-person-add">Related: add (`add`)</a>
      <a class="btn-ghost" href="#editing-a-person-edit">Related: edit (`edit`)</a>
    </div>
  </div>

  <div class="roadmap-card">
    <h4>📝 Interview notes <span class="chip planned">Planned</span></h4>
    <div class="roadmap-meta">
      <span>Goal: keep prep & takeaways beside the interview</span>
    </div>
    <strong>Why it matters</strong>
    <ul class="why">
      <li>No more jumping between apps to find context and follow-ups.<br/><br/></li>
    </ul>
    <strong>You’ll be able to:</strong>
    <ul class="you-can">
      <li>Add bullet points, links, and quick tags to each interview.</li>
      <li>See notes when listing or opening interviews.</li>
    </ul>
    <div class="roadmap-actions">
      <a class="btn-ghost" href="#listing-all-interviews-of-a-contact-listinterview">Related: listInterview</a>
      <a class="btn-ghost" href="#adding-an-interview-to-a-contact-addinterview">Related: addInterview</a>
    </div>
  </div>

</div>

</panel>

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE e/EMAIL o/ORGANISATION r/ROLE [c/CATEGORY]…​` <br> e.g., `add n/John Doe p/98765432 e/johnd@example.com o/NUS r/Student c/friends c/owesMoney`
**Clear** | `clear`
**Delete** | `delete i/INDEX`<br> e.g., `delete i/3`
**Edit** | `edit i/INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [o/ORGANISATION] [r/ROLE]​`<br> e.g.,`edit i/2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James NUS Student`
**List** | `list`
**AddInterview** | `addInterview i/INDEX h/HEADER d/DATE t/TIME l/LOCATION` <br> e.g., `addInterview i/1 h/Interview with ABC Corp d/2024-10-10 t/14:00 l/123, Business St, #02-25`
**DeleteInterview** | `deleteInterview i/PERSON_INDEX v/INTERVIEW_INDEX` <br> e.g., `deleteInterview i/1 v/2`
**ListInterview** | `listInterview i/INDEX` <br> e.g., `listInterview i/1`
**AddCat** | `addCat i/INDEX [c/CATEGORY]...`<br>e.g., `addCat i/1 c/emergency`
**DeleteCat** | `deleteCat i/INDEX [c/CATEGORY]...`<br>e.g., `deleteCat i/1 c/emergency`
**NextInterview** | `nextInterview`
**Exit** | `exit`
**Help** | `help`
