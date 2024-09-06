# Builder stage
# FROM clojure:temurin-21-tools-deps-1.11.4.1474-bookworm-slim AS builder
FROM clojure:temurin-21-tools-deps-bookworm-slim AS builder

# Install necessary packages for building
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    r-base \
    r-base-dev \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install Quarto
RUN wget https://github.com/quarto-dev/quarto-cli/releases/download/v1.3.450/quarto-1.3.450-linux-amd64.deb \
    && dpkg -i quarto-1.3.450-linux-amd64.deb \
    && rm quarto-1.3.450-linux-amd64.deb \
    && apt-get update \
    && apt-get install -f -y \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install R packages
RUN R -e "install.packages(c('ggplot2', 'Rserve', 'svglite'), repos='https://cran.rstudio.com/', Ncpus=parallel::detectCores())"

# Final stage
# FROM clojure:temurin-21-tools-deps-1.11.4.1474-bookworm-slim
FROM clojure:temurin-21-tools-deps-bookworm-slim

# Copy Quarto, R, and R packages from builder
COPY --from=builder /usr/local/bin/quarto /usr/local/bin/quarto
COPY --from=builder /usr/lib/R /usr/lib/R
COPY --from=builder /usr/share/R /usr/share/R
COPY --from=builder /usr/bin/R /usr/bin/R
COPY --from=builder /usr/bin/Rscript /usr/bin/Rscript
COPY --from=builder /usr/local/lib/R /usr/local/lib/R

# Install necessary packages including X11 libraries and R
RUN apt-get update && apt-get install -y --no-install-recommends \
    bash \
    openssh-client \
    openssh-server \
    sudo \
    libx11-dev \
    libxi6 \
    libxtst6 \
    libxrender1 \
    libxext6 \
    libxft2 \
    x11-apps \
    r-base \
    r-base-dev \
    # for Quarto
    ca-certificates \ 
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Create a non-root user and set up sudo privileges
RUN useradd -ms /bin/bash vscode \
    && echo "vscode ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Set the working directory inside the container
WORKDIR /workspace/probability

# Switch to the non-root user
USER vscode

# Add NVIDIA GPU support
ENV NVIDIA_VISIBLE_DEVICES=all
ENV NVIDIA_DRIVER_CAPABILITIES=compute,utility

# Set DISPLAY environment variable (might be needed for some GUI applications)
ENV DISPLAY=host.docker.internal:0

# Ensure the Java AWT libraries can find the X11 libraries
ENV LD_LIBRARY_PATH=/usr/lib/x86_64-linux-gnu:$LD_LIBRARY_PATH

# Set Java to run in headless mode by default
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true"

# Add Quarto and R to PATH
ENV PATH="/usr/local/bin:/usr/bin:${PATH}"

# Set R_HOME environment variable
ENV R_HOME=/usr/lib/R

# Verify Quarto installation
RUN quarto check install
