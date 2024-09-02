# F24-M6410-HW1

### Jaryt Salvo
**Date:** 9-2-2024

**Fall 2024 | MATH6410 Probability Theory**

*************

The code in the `src/assignments` folder were rendered with [Clay](https://scicloj.github.io/clay/) and deployed with [Github Pages](https://pages.github.com/). 

## **[Please click HERE to view the workbook](https://adabwana.github.io/f24-m6410-hw1/).**

## Running the code

To play in this project, you'll need to install Docker and/or DevPod (optional). 

   - [Install Docker](https://docs.docker.com/get-docker/)
   - [Install DevPod](https://devpod.sh/)

> **Option 1 is recommended in terms of easier setup.**

### **Option 1: Remote-Centric** 

1. Install [Docker](https://docs.docker.com/get-docker/) and [DevPod](https://devpod.sh/) and open DevPod.

2. In DevPod, go to `Workspaces`, click `+ Create`, in the `Git Repo` field, enter `https://github.com/adabwana/f24-m6410-hw1`, ensure `Docker` is selected in `Provider`, Name your workspace (e.g. `f24-m6410-hw1`), and click the `Create Workspace` button. The project will open in your browser using the provided `.devcontainer` configuration.

### **Note**: 

VSCode server will popup on `localhost:10800` in your *default* browser. Chrome-based browsers work better for VSCode server. I found in Firefox, VSCode hotkeys didn't work, instead it used Firefox's default bindings. 

If VSCode hotkeys are not working in Chrome, viz. `Ctrl + Alt + C` then `Ctrl + Alt + J` to `Calva: Jack-In`, you may need to, on the right-side of the URL bar, click the `^` button to open the "App" view ("PWA"), and then click the `Open Remote Window`. Now try `Calva: Jack-In` again: `Ctrl + Alt + C` then `Ctrl + Alt + J`.


### -------- **OR** --------


### **Option 2: Local-Centric** 

1. Install [Docker](https://docs.docker.com/get-docker/) and open VSCode:

2. Install the VSCode extension [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers). Press `Ctrl` `+` `` ` `` to open the terminal in VSCode.

3. In the VSCode terminal, clone the repository to `Documents/projects`, for example:

   ```bash
   cd .\Documents\projects
   git clone https://github.com/adabwana/f24-m6410-hw1.git
   cd f24-m6410-hw1
   code .
   ```

4. When VSCode reopens, you can find a popup on the bottom right saying "Open in Dev Container". If not, press `Ctrl + Shift + P` and type and select `Dev Containers: Open Folder in Container`. Navigate to the folder and open it.

## Getting started with Clojure
To get started with Clojure, use the following commands:

> - `Ctrl + Alt + C` then `Ctrl + Alt + J` to "Jack-in" and connect to the REPL (Read-Eval-Print Loop).
> - Select `deps.edn` in the `Connect Sequence` dropdown menu.
> - Check the `dev` alias in the `Connect Sequence` dropdown menu.
> - Press `OK`.

**Note**: We include `dev` alias in project so that we can use `Clay` for rendering with `Ctrl + Alt + SPACE` then `n` to render the namespace in the browser. TODO?? devcontainer.json to define [`Clay` keybindings](https://github.com/scicloj/clay/blob/main/resources/calva.exports/config.edn).

A new window with a connected REPL will open. From there, you can run the code in the `src/assignments` folder. You can run code in the clj files with different level of evaluation. See [Calva's user guide](https://calva.io/eval-tips/) for examples. However the most used are:

> - `Alt + Enter` to evaluate the top level form. ;; *i use this most*
> - `Ctrl + Enter` to evaluate the current form.
> - `Ctrl + Alt + C` then `Enter` to evaluate the entire namespace.

Using the `Clay` rendering hotkeys, you can render the workbook with different formats. My two most used `Clay` keybindings are:

> - `Ctrl + Alt + SPACE` then `n` to render the namespace.
> - `Ctrl + Alt + SPACE` then `,` to render current form.

***You can change the hotkey in the `Settings` menu.***